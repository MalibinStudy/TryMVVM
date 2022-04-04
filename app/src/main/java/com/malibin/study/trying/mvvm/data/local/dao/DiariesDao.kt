package com.malibin.study.trying.mvvm.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.malibin.study.trying.mvvm.data.local.entity.DiaryEntity

@Dao
interface DiariesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDiary(diary: DiaryEntity)

    @Query("SELECT * FROM diaryEntity WHERE id = :diaryId")
    suspend fun getDiary(diaryId: String): DiaryEntity?

    @Query("SELECT * FROM diaryEntity")
    suspend fun getAllDiaries(): List<DiaryEntity>

    @Update
    suspend fun updateDiary(diary: DiaryEntity)

    @Delete
    suspend fun deleteDiary(diary: DiaryEntity)

    @Query("DELETE FROM diaryEntity WHERE id = :diaryId")
    suspend fun deleteDiary(diaryId: String)
}
