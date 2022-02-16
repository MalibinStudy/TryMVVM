package com.malibin.study.trying.mvvm.data.source.local

import com.malibin.study.trying.mvvm.data.dao.DiariesDao
import com.malibin.study.trying.mvvm.data.entity.DiaryEntity
import com.malibin.study.trying.mvvm.data.source.DiariesSource

/**
 * Created By Malibin
 * on 9월 11, 2021
 */

class DiariesLocalSource(
    private val diariesDao: DiariesDao,
) : DiariesSource {

    override fun createDiary(diaryEntity: DiaryEntity) {
        diariesDao.insertDiary(diaryEntity)
    }

    override fun getAllDiaries(): List<DiaryEntity> {
        return diariesDao.getAllDiaries()
    }

    override fun getDiaryOf(diaryId: String): DiaryEntity {
        return diariesDao.getDiary(diaryId)
    }

    override fun updateDiary(diaryEntity: DiaryEntity) {
        diariesDao.updateDiary(diaryEntity)
    }

    override fun deleteDiaryOf(diaryId: String) {
        diariesDao.deleteDiary(diaryId)
    }
}
