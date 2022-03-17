package com.malibin.study.trying.mvvm.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.malibin.study.trying.mvvm.databinding.ActivityLiveDataTestBinding

class LiveDataTestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLiveDataTestBinding

    private val liveDataTest = LiveDataTest()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLiveDataTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
