package com.malibin.study.trying.mvvm.data.source

import com.malibin.study.trying.mvvm.domain.Diary

interface DiariesSource {
    suspend fun getAllDiaries(): Result<List<Diary>>

    suspend fun getDiary(id: String): Result<Diary?>

    suspend fun saveDiary(diary: Diary): Result<Unit>

    suspend fun updateDiary(diary: Diary): Result<Unit>

    suspend fun deleteDiary(diaryId: String): Result<Unit>
}
