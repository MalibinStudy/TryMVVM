package com.malibin.study.trying.mvvm

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.malibin.study.trying.mvvm.data.db.DailyDiaryDatabase
import com.malibin.study.trying.mvvm.data.entity.Diary
import com.malibin.study.trying.mvvm.data.source.DiariesSource
import com.malibin.study.trying.mvvm.data.source.local.DiariesLocalSource

class MainActivity : AppCompatActivity() {

    private lateinit var database: DailyDiaryDatabase
    private lateinit var diariesSource: DiariesSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = DailyDiaryDatabase.newInstance(this)
        diariesSource = DiariesLocalSource(database.getDiariesDao())

        val diary = Diary("오늘의 일기", "Room을 배웠다. -끝-")

        createDiary(diary)
        updateDiary(diary)
        deleteDiary(diary)
    }

    private fun createDiary(diary: Diary) {
        diariesSource.createDiary(diary)
        showAllDiaries()
    }

    private fun updateDiary(diary: Diary) {
        val updatedDiary = diary.copy(content = "update로 바꿨다 ㅎㅎ")
        diariesSource.updateDiary(updatedDiary)
        showAllDiaries()
    }

    private fun deleteDiary(diary: Diary) {
        diariesSource.deleteDiaryOf(diary.id)
        showAllDiaries()
    }

    private fun showAllDiaries() {
        Log.d("MalibinDebug", "${diariesSource.getAllDiaries()}")
    }
}
