package com.malibin.study.trying.mvvm.presentation

import android.os.Handler
import android.os.HandlerThread
import androidx.databinding.ObservableInt

class LiveDataTest {

    //    private val _count = MutableLiveData<Int>()
    val count = ObservableInt(0)

    init {
        val handlerThread = HandlerThread("back")
            .also { it.start() }

        Handler(handlerThread.looper).post {
            while (true) {
                Thread.sleep(2_000)
                count.set(count.get() + 1)
            }
        }
    }
}
