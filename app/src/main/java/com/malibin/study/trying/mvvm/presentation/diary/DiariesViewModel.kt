package com.malibin.study.trying.mvvm.presentation.diary

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.malibin.study.trying.mvvm.data.local.dao.DiariesDao
import com.malibin.study.trying.mvvm.data.local.db.DailyDiaryDatabase
import com.malibin.study.trying.mvvm.domain.Diary
import kotlinx.coroutines.launch

class DiariesViewModel(application: Application) : AndroidViewModel(application) {

    private val diariesDao: DiariesDao = DailyDiaryDatabase.getInstance(application).getDiariesDao()

    private val _diaries = MutableLiveData<List<Diary>>()
    val diaries: LiveData<List<Diary>> = _diaries

    init {
        loadDiaries()
    }

    fun loadDiaries() = viewModelScope.launch {
        _diaries.value = diariesDao.getAllDiaries()
            .map { Diary(it.id, it.title, it.content, it.createDate) }
    }
}
