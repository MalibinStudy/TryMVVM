package com.malibin.study.trying.mvvm.presentation.diary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malibin.study.trying.mvvm.domain.Diary
import com.malibin.study.trying.mvvm.domain.repository.DiariesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiariesViewModel @Inject constructor(
    private val diariesRepository: DiariesRepository,
) : ViewModel() {
    private val _diaries = MutableLiveData<List<Diary>>()
    val diaries: LiveData<List<Diary>> = _diaries

    init {
        loadDiaries()
    }

    fun loadDiaries() = viewModelScope.launch {
        diariesRepository.getAllDiaries()
            .onSuccess { _diaries.value = it }
            .onFailure { /* do something */ }
    }
}
