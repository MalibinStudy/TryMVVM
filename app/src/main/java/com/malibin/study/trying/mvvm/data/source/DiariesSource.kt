package com.malibin.study.trying.mvvm.data.source

import com.malibin.study.trying.mvvm.data.entity.Diary

/**
 * Created By Malibin
 * on 9월 11, 2021
 */

interface DiariesSource {

    fun createDiary(diary: Diary)

    fun getAllDiaries(): List<Diary>

    fun getDiaryOf(diaryId: String): Diary

    fun updateDiary(diary: Diary)

    fun deleteDiaryOf(diaryId: String)
}
