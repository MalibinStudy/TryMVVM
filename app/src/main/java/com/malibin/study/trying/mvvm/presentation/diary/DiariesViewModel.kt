package com.malibin.study.trying.mvvm.presentation.diary

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.malibin.study.trying.mvvm.data.local.db.DailyDiaryDatabase
import com.malibin.study.trying.mvvm.domain.Diary

class DiariesViewModel(application: Application) : AndroidViewModel(application) {

    val diaries: LiveData<List<Diary>> = DailyDiaryDatabase.newInstance(application)
        .getDiariesDao()
        .getAllDiaries()
        .map {
            it.map {
                Diary(
                    id = it.id,
                    title = it.title,
                    content = it.content,
                    createDate = it.createDate,
                )
            }
        }
}
