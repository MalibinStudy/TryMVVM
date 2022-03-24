package com.malibin.study.trying.mvvm.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.malibin.study.trying.mvvm.databinding.ActivityLiveDataTestBinding
import kotlinx.coroutines.launch

class LiveDataTestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLiveDataTestBinding

    private val liveDataTest = LiveDataTest()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLiveDataTestBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.test = liveDataTest
        setContentView(binding.root)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                liveDataTest.count.collect {
                    Log.d("MalibinDebug", "count : $it")
                }
            }
        }
    }
}
