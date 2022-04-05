package com.malibin.study.trying.mvvm.data.remote.params

data class SaveDiaryParams(
    val title: String,
    val content: String,
    val createdAt: Long,
    val id: String,
)
