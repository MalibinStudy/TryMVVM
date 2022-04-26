package com.malibin.study.trying.mvvm.di

import com.malibin.study.trying.mvvm.data.local.source.DiariesLocalSource
import com.malibin.study.trying.mvvm.data.source.DiariesSource
import com.malibin.study.trying.mvvm.di.annotations.LocalDiariesSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface LocalSourceModule {

    @Binds
    @Singleton
    @LocalDiariesSource
    fun bindsDiariesLocalSource(source: DiariesLocalSource): DiariesSource
}
