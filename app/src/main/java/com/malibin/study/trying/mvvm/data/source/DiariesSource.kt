package com.malibin.study.trying.mvvm.data.source

import com.malibin.study.trying.mvvm.data.entity.DiaryEntity

/**
 * Created By Malibin
 * on 9월 11, 2021
 */

interface DiariesSource {

    fun createDiary(diaryEntity: DiaryEntity)

    fun getAllDiaries(): List<DiaryEntity>

    fun getDiaryOf(diaryId: String): DiaryEntity

    fun updateDiary(diaryEntity: DiaryEntity)

    fun deleteDiaryOf(diaryId: String)
}
