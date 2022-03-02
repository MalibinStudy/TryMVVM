package com.malibin.study.trying.mvvm.presentation.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("app:createdDate")
fun bindDiaryCreatedDate(textView: TextView, date: Date?) {
    if (date == null) return
    val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)
    textView.text = simpleDateFormat.format(date)
}
