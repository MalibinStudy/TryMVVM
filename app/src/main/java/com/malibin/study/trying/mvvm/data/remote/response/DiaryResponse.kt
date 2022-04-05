package com.malibin.study.trying.mvvm.data.remote.response

data class DiaryResponse(
    val id: String,
    val title: String,
    val content: String,
    val createdAt: Long,
)
