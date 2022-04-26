package com.malibin.study.trying.mvvm.di

import com.malibin.study.trying.mvvm.data.remote.source.DiariesRemoteSource
import com.malibin.study.trying.mvvm.data.source.DiariesSource
import com.malibin.study.trying.mvvm.di.annotations.RemoteDiariesSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RemoteSourceModule {

    @Binds
    @Singleton
    @RemoteDiariesSource
    fun bindsDiariesRemoteSource(source: DiariesRemoteSource): DiariesSource
}
