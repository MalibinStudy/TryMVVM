package com.malibin.study.trying.mvvm.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.malibin.study.trying.mvvm.data.entity.DiaryEntity

/**
 * Created By Malibin
 * on 9월 04, 2021
 */

@Dao
interface DiariesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDiary(diaryEntity: DiaryEntity)

    @Query("SELECT * FROM DiaryEntity WHERE id = :diaryId")
    fun getDiary(diaryId: String): DiaryEntity

    @Query("SELECT * FROM DiaryEntity")
    fun getAllDiaries(): List<DiaryEntity>

    @Update
    fun updateDiary(diaryEntity: DiaryEntity)

    @Delete
    fun deleteDiary(diaryEntity: DiaryEntity)

    @Query("DELETE FROM DiaryEntity WHERE id = :diaryId")
    fun deleteDiary(diaryId: String)
}
