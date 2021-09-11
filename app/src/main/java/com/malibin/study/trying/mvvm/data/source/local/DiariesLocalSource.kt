package com.malibin.study.trying.mvvm.data.source.local

import com.malibin.study.trying.mvvm.data.dao.DiariesDao
import com.malibin.study.trying.mvvm.data.entity.Diary
import com.malibin.study.trying.mvvm.data.source.DiariesSource

/**
 * Created By Malibin
 * on 9월 11, 2021
 */

class DiariesLocalSource(
    private val diariesDao: DiariesDao,
) : DiariesSource {

    override fun createDiary(diary: Diary) {
        diariesDao.insertDiary(diary)
    }

    override fun getAllDiaries(): List<Diary> {
        return diariesDao.getAllDiaries()
    }

    override fun getDiaryOf(diaryId: String): Diary {
        return diariesDao.getDiary(diaryId)
    }

    override fun updateDiary(diary: Diary) {
        diariesDao.updateDiary(diary)
    }

    override fun deleteDiaryOf(diaryId: String) {
        diariesDao.deleteDiary(diaryId)
    }
}
