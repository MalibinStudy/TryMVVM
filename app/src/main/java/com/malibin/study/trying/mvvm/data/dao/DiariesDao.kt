package com.malibin.study.trying.mvvm.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.malibin.study.trying.mvvm.data.entity.Diary

/**
 * Created By Malibin
 * on 9월 04, 2021
 */

@Dao
interface DiariesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDiary(diary: Diary)

    @Query("SELECT * FROM diary WHERE id = :diaryId")
    fun getDiary(diaryId: String): Diary

    @Query("SELECT * FROM diary")
    fun getAllDiaries(): List<Diary>

    @Update
    fun updateDiary(diary: Diary)

    @Delete
    fun deleteDiary(diary: Diary)

    @Query("DELETE FROM diary WHERE id = :diaryId")
    fun deleteDiary(diaryId: String)
}
