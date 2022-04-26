package com.malibin.study.trying.mvvm.di

import android.content.Context
import androidx.room.Room
import com.malibin.study.trying.mvvm.data.local.dao.DiariesDao
import com.malibin.study.trying.mvvm.data.local.db.DailyDiaryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun providesDailyDiaryDatabase(@ApplicationContext context: Context): DailyDiaryDatabase {
        return Room.databaseBuilder(
            context,
            DailyDiaryDatabase::class.java,
            "DailyDiaryDatabase"
        ).build()
    }

    @Provides
    @Singleton
    fun providesDiariesDao(database: DailyDiaryDatabase): DiariesDao {
        return database.getDiariesDao()
    }
}
