package com.malibin.study.trying.mvvm.presentation.diary.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.malibin.study.trying.mvvm.data.DiaryMemory
import com.malibin.study.trying.mvvm.domain.Diary
import com.malibin.study.trying.mvvm.presentation.utils.SingleLiveEvent

class EditDiaryViewModel : ViewModel() {

    val editSuccessEvent = SingleLiveEvent<Unit>()

    val title = MutableLiveData("")
    val content = MutableLiveData("")

    private val _diary = MutableLiveData(Diary.createEmpty())
    val diary: LiveData<Diary> = _diary

    fun loadDiary(diaryId: String? = null) {
        val diary = DiaryMemory.getDiary(diaryId ?: return)
        _diary.value = diary
        title.value = diary.title
        content.value = diary.content
    }

    fun saveDiary() {
        val previousDiary = _diary.value ?: error("diary cannot be null")
        val updatedDiary = previousDiary.copy(
            title = title.value.orEmpty(),
            content = content.value.orEmpty(),
        )
        DiaryMemory.saveDiary(updatedDiary)
        editSuccessEvent.value = Unit
    }
}
