package com.malibin.study.trying.mvvm.di.annotations

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalDiariesSource

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteDiariesSource
