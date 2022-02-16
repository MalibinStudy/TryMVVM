package com.malibin.study.trying.mvvm.presentation.memo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.malibin.study.trying.mvvm.databinding.ActivityEditDiaryBinding

class EditDiaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditDiaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
