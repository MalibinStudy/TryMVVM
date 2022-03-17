package com.malibin.study.trying.mvvm.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class LiveDataTest {

    private val _count =  MutableLiveData<Int>()
    val count: LiveData<Int> = _count
}
