package com.malibin.study.trying.mvvm.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Created By Malibin
 * on 9월 04, 2021
 */

@Entity
data class Diary(
    val title: String,
    val content: String,
    val createDate: Date = Date(),
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
)
