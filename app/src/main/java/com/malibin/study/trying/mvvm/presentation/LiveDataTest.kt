package com.malibin.study.trying.mvvm.presentation

import android.os.Handler
import android.os.HandlerThread
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LiveDataTest {

    private val _count = MutableStateFlow(0)
    val count: StateFlow<Int> = _count

    init {
        val handlerThread = HandlerThread("back")
            .also { it.start() }

        Handler(handlerThread.looper).post {
            while (true) {
                Thread.sleep(2_000)
                _count.value = _count.value + 1
            }
        }
    }
}
