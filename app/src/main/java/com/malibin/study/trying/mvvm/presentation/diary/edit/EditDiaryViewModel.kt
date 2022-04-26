package com.malibin.study.trying.mvvm.presentation.diary.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malibin.study.trying.mvvm.domain.Diary
import com.malibin.study.trying.mvvm.domain.repository.DiariesRepository
import com.malibin.study.trying.mvvm.presentation.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditDiaryViewModel @Inject constructor(
    private val diariesRepository: DiariesRepository,
) : ViewModel() {
    val editSuccessEvent = SingleLiveEvent<Unit>()

    val title = MutableLiveData("")
    val content = MutableLiveData("")

    private val _diary = MutableLiveData(Diary.createEmpty())
    val diary: LiveData<Diary> = _diary

    fun loadDiary(diaryId: String? = null) = viewModelScope.launch {
        diariesRepository.getDiary(diaryId ?: return@launch)
            .onSuccess {
                _diary.value = it
                title.value = it.title
                content.value = it.content
            }
            .onFailure { }
    }

    fun saveDiary() = viewModelScope.launch {
        val previousDiary = _diary.value ?: error("diary cannot be null")
        val newDiary = Diary(
            id = previousDiary.id,
            title = title.value.orEmpty(),
            content = content.value.orEmpty(),
            createDate = previousDiary.createDate,
        )
        diariesRepository.saveDiary(newDiary)
            .onSuccess { editSuccessEvent.value = Unit }
            .onFailure { }
    }
}
