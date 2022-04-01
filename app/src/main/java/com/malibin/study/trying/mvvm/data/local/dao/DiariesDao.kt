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
    fun insertDiary(diary: DiaryEntity)

    @Query("SELECT * FROM diaryEntity WHERE id = :diaryId")
    fun getDiary(diaryId: String): DiaryEntity

    @Query("SELECT * FROM diaryEntity")
    fun getAllDiaries(): List<DiaryEntity>

    @Update
    fun updateDiary(diary: DiaryEntity)

    @Delete
    fun deleteDiary(diary: DiaryEntity)

    @Query("DELETE FROM diaryEntity WHERE id = :diaryId")
    fun deleteDiary(diaryId: String)
}
