package com.malibin.study.trying.mvvm.domain

import java.util.*

data class Memo(
    val id: Long,
    val title: String,
    val content: String,
    val createDate: Date,
)
