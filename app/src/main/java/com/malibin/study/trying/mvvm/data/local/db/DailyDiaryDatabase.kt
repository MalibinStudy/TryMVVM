package com.malibin.study.trying.mvvm.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.malibin.study.trying.mvvm.data.local.converter.DateTypeConverter
import com.malibin.study.trying.mvvm.data.local.dao.DiariesDao
import com.malibin.study.trying.mvvm.data.local.entity.DiaryEntity

@TypeConverters(DateTypeConverter::class)
@Database(entities = [DiaryEntity::class], version = 1)
abstract class DailyDiaryDatabase : RoomDatabase() {

    abstract fun getDiariesDao(): DiariesDao

    companion object {
        private var instance: DailyDiaryDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): DailyDiaryDatabase {
            return instance ?: synchronized(this) {
                newInstance(context).also { instance = it }
            }
        }

        @JvmStatic
        fun newInstance(context: Context): DailyDiaryDatabase {
            return Room.databaseBuilder(
                context,
                DailyDiaryDatabase::class.java,
                "DailyDiaryDatabase"
            ).build()
        }
    }
}
