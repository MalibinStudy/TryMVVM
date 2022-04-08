package com.malibin.study.trying.mvvm.presentation.diary

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.malibin.study.trying.mvvm.data.local.dao.DiariesDao
import com.malibin.study.trying.mvvm.data.local.db.DailyDiaryDatabase
import com.malibin.study.trying.mvvm.data.local.entity.DiaryEntity
import com.malibin.study.trying.mvvm.data.remote.service.MalibinService
import com.malibin.study.trying.mvvm.domain.Diary
import kotlinx.coroutines.launch
import java.util.*

class DiariesViewModel(application: Application) : AndroidViewModel(application) {

    private val diariesDao: DiariesDao = DailyDiaryDatabase.getInstance(application).getDiariesDao()
    private val malibinService: MalibinService = MalibinService.getInstance()

    private val _diaries = MutableLiveData<List<Diary>>()
    val diaries: LiveData<List<Diary>> = _diaries

    init {
        loadDiaries()
    }

    fun loadDiaries() = viewModelScope.launch {
        _diaries.value = getLocalDiaries().takeIf { it.isNotEmpty() }
            ?: getRemoteDiaries().onEach { diariesDao.insertDiary(it.toDiaryEntity()) }
    }

    private suspend fun getLocalDiaries(): List<Diary> {
        return diariesDao.getAllDiaries()
            .map { Diary(it.id, it.title, it.content, it.createDate) }
    }

    private suspend fun getRemoteDiaries(): List<Diary> {
        val response = malibinService.getDiaries()
        return if (response.isSuccessful) {
            response.body().orEmpty()
                .map { Diary(it.id, it.title, it.content, Date(it.createdAt)) }
        } else emptyList()
    }

    private fun Diary.toDiaryEntity(): DiaryEntity = DiaryEntity(
        title = this.title,
        content = this.content,
        createDate = this.createDate,
        id = this.id,
    )
}
