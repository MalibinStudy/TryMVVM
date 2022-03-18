package com.malibin.study.trying.mvvm.presentation

import android.os.Handler
import android.os.HandlerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class LiveDataTest {

    private val _count = MutableLiveData<Int>()
    val count: LiveData<Int> = _count

    init {
        val handlerThread = HandlerThread("back")
            .also { it.start() }

        Handler(handlerThread.looper).post {
            while (true) {
                Thread.sleep(2_000)
                _count.postValue((_count.value ?: 0) + 1)

//                Handler(Looper.getMainLooper()).post {
//                    _count.value = (_count.value ?: 0) + 1
//                }
            }
        }

//        Thread{
//            while (true) {
//                Thread.sleep(2_000)
//                _count.postValue((_count.value ?: 0) + 1)
//            }
//        }.start()
    }
}
