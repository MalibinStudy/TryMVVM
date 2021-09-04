package com.malibin.study.trying.mvvm.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.malibin.study.trying.mvvm.data.dao.DiariesDao
import com.malibin.study.trying.mvvm.data.entity.Diary

/**
 * Created By Malibin
 * on 9월 04, 2021
 */

@TypeConverters(DateTypeConverter::class)
@Database(entities = [Diary::class], version = 1)
abstract class DailyDiaryDatabase : RoomDatabase() {

    abstract fun getDiariesDao(): DiariesDao

    companion object {
        @JvmStatic
        fun newInstance(context: Context): DailyDiaryDatabase {
            return Room.databaseBuilder(
                context,
                DailyDiaryDatabase::class.java,
                "DailyDiaryDatabase"
            ).allowMainThreadQueries()
                .build()
        }
    }
}
