package com.malibin.study.trying.mvvm.presentation.diary.edit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.malibin.study.trying.mvvm.data.local.dao.DiariesDao
import com.malibin.study.trying.mvvm.data.local.db.DailyDiaryDatabase
import com.malibin.study.trying.mvvm.data.local.entity.DiaryEntity
import com.malibin.study.trying.mvvm.domain.Diary
import com.malibin.study.trying.mvvm.presentation.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class EditDiaryViewModel(application: Application) : AndroidViewModel(application) {

    private val diariesDao: DiariesDao = DailyDiaryDatabase.getInstance(application).getDiariesDao()

    val editSuccessEvent = SingleLiveEvent<Unit>()

    val title = MutableLiveData("")
    val content = MutableLiveData("")

    private val _diary = MutableLiveData(Diary.createEmpty())
    val diary: LiveData<Diary> = _diary

    fun loadDiary(diaryId: String? = null) = viewModelScope.launch {
        val diary = diariesDao.getDiary(diaryId ?: return@launch)
            ?.let { Diary(it.id, it.title, it.content, it.createDate) }
        _diary.value = diary
        title.value = diary?.title
        content.value = diary?.content
    }

    fun saveDiary() = viewModelScope.launch {
        val previousDiary = _diary.value ?: error("diary cannot be null")
        val diaryEntity = DiaryEntity(
            id = previousDiary.id,
            title = title.value.orEmpty(),
            content = content.value.orEmpty(),
            createDate = previousDiary.createDate,
        )
        diariesDao.insertDiary(diaryEntity)
        editSuccessEvent.value = Unit
    }
}
