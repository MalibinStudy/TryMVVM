package com.malibin.study.trying.mvvm.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.Observable
import androidx.databinding.ObservableInt
import androidx.lifecycle.liveData
import com.malibin.study.trying.mvvm.databinding.ActivityLiveDataTestBinding

class LiveDataTestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLiveDataTestBinding

    private val liveDataTest = LiveDataTest()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLiveDataTestBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.test = liveDataTest
        setContentView(binding.root)

        liveDataTest.count.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                sender as ObservableInt
                Log.d("MalibinDebug", "count : ${sender.get()}")
            }
        })
    }
}
