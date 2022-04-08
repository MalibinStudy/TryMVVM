package com.malibin.study.trying.mvvm.presentation.diary.edit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.malibin.study.trying.mvvm.data.local.dao.DiariesDao
import com.malibin.study.trying.mvvm.data.local.db.DailyDiaryDatabase
import com.malibin.study.trying.mvvm.data.local.entity.DiaryEntity
import com.malibin.study.trying.mvvm.data.remote.params.SaveDiaryParams
import com.malibin.study.trying.mvvm.data.remote.service.MalibinService
import com.malibin.study.trying.mvvm.domain.Diary
import com.malibin.study.trying.mvvm.presentation.utils.SingleLiveEvent
import kotlinx.coroutines.launch
import java.util.*

class EditDiaryViewModel(application: Application) : AndroidViewModel(application) {

    private val diariesDao: DiariesDao = DailyDiaryDatabase.getInstance(application).getDiariesDao()
    private val malibinService: MalibinService = MalibinService.getInstance()

    val editSuccessEvent = SingleLiveEvent<Unit>()

    val title = MutableLiveData("")
    val content = MutableLiveData("")

    private val _diary = MutableLiveData(Diary.createEmpty())
    val diary: LiveData<Diary> = _diary

    fun loadDiary(diaryId: String? = null) = viewModelScope.launch {
        val diary: Diary? = loadLocalDiary(diaryId ?: return@launch)
            ?: loadRemoteDiary(diaryId)
        _diary.value = diary
        title.value = diary?.title
        content.value = diary?.content
    }

    private suspend fun loadLocalDiary(diaryId: String): Diary? {
        return diariesDao.getDiary(diaryId)
            ?.let { Diary(it.id, it.title, it.content, it.createDate) }
    }

    private suspend fun loadRemoteDiary(diaryId: String): Diary? {
        val response = malibinService.getDiary(diaryId)
        return if (response.isSuccessful) {
            response.body()
                ?.let { Diary(it.id, it.title, it.content, Date(it.createdAt)) }
        } else null
    }

    fun saveDiary() = viewModelScope.launch {
        val previousDiary = _diary.value ?: error("diary cannot be null")
        saveDiaryLocal(previousDiary)
        saveDiaryRemote(previousDiary)
        editSuccessEvent.value = Unit
    }

    private suspend fun saveDiaryLocal(diary: Diary) {
        val diaryEntity = DiaryEntity(
            id = diary.id,
            title = title.value.orEmpty(),
            content = content.value.orEmpty(),
            createDate = diary.createDate,
        )
        diariesDao.insertDiary(diaryEntity)
    }

    private suspend fun saveDiaryRemote(diary: Diary) {
        val diaryParams = SaveDiaryParams(
            id = diary.id,
            title = title.value.orEmpty(),
            content = content.value.orEmpty(),
            createdAt = diary.createDate.time,
        )
        malibinService.saveDiary(params = diaryParams)
    }
}
