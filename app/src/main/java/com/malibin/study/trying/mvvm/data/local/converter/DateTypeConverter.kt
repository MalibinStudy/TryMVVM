package com.malibin.study.trying.mvvm.data.local.converter

import androidx.room.TypeConverter
import java.util.*

class DateTypeConverter {
    @TypeConverter
    fun toDate(milliseconds: Long?): Date? {
        return Date(milliseconds ?: return null)
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? = date?.time
}
