package com.malibin.study.trying.mvvm.presentation.diary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.malibin.study.trying.mvvm.data.DiaryMemory
import com.malibin.study.trying.mvvm.domain.Diary

class DiariesViewModel : ViewModel() {

    private val _diaries = MutableLiveData(listOf<Diary>())
    val diaries: LiveData<List<Diary>> = _diaries

    init {
        loadDiaries()
    }

    fun loadDiaries() {
        _diaries.value = DiaryMemory.getAllDiaries()
    }
}
