package com.malibin.study.trying.mvvm.presentation.diary

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.malibin.study.trying.mvvm.data.local.db.DailyDiaryDatabase
import com.malibin.study.trying.mvvm.domain.Diary

class DiariesViewModel(application: Application) : AndroidViewModel(application) {

    val diaries: LiveData<List<Diary>> = DailyDiaryDatabase.getInstance(application)
        .getDiariesDao()
        .getAllDiaries()
        .map { list ->
            list.map {
                Diary(
                    id = it.id,
                    title = it.title,
                    content = it.content,
                    createDate = it.createDate,
                )
            }
        }
}
