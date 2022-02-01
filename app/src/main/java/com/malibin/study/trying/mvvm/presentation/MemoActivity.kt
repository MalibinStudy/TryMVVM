package com.malibin.study.trying.mvvm.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.malibin.study.trying.mvvm.databinding.ActivityMemoBinding

class MemoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
