package com.malibin.study.trying.mvvm.data.db

import androidx.room.TypeConverter
import java.util.*

/**
 * Created By Malibin
 * on 9월 04, 2021
 */

class DateTypeConverter {
    @TypeConverter
    fun toDate(milliseconds: Long?): Date? {
        return Date(milliseconds ?: return null)
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? = date?.time
}
