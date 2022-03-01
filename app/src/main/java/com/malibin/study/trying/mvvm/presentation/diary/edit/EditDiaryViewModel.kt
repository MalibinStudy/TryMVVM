package com.malibin.study.trying.mvvm.presentation.diary.edit

import androidx.lifecycle.*
import com.malibin.study.trying.mvvm.domain.Diary
import com.malibin.study.trying.mvvm.presentation.utils.SingleLiveEvent

class EditDiaryViewModel: ViewModel() {

    val editSuccessEvent = SingleLiveEvent<Unit>()

    val title = MutableLiveData("")
    val content = MutableLiveData("")

    private val _diary = MutableLiveData<Diary>()
    val diary: LiveData<Diary> = _diary

    fun loadDiary(diary: Diary? = null) {
        _diary.value = diary ?: Diary.createEmpty()
        title.value = diary?.title.orEmpty()
        content.value = diary?.content.orEmpty()
    }

    fun saveDiary() {
        val previousDiary = _diary.value ?: error("diary cannot be null")
        val updatedDiary = previousDiary.copy(
                title = title.value.orEmpty(),
                content = content.value.orEmpty(),
        )
        editSuccessEvent.value = Unit
    }
}