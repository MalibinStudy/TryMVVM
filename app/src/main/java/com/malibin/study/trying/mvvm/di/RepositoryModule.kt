package com.malibin.study.trying.mvvm.di

import com.malibin.study.trying.mvvm.data.repository.RealDiariesRepository
import com.malibin.study.trying.mvvm.domain.repository.DiariesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindsDiariesRepository(repository: RealDiariesRepository): DiariesRepository
}
