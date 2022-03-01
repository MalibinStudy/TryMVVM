package com.malibin.study.trying.mvvm.presentation.diary.edit

import android.app.Activity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.malibin.study.trying.mvvm.databinding.ActivityEditDiaryBinding

class EditDiaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditDiaryBinding

    private val editDiaryViewModel: EditDiaryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditDiaryBinding.inflate(layoutInflater)
        binding.viewModel = editDiaryViewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)

        editDiaryViewModel.loadDiary(getDiaryId())

        editDiaryViewModel.editSuccessEvent.observe(this) {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    private fun getDiaryId(): String? {
        return intent.getStringExtra(KEY_DIARY)
    }

    companion object {
        const val KEY_DIARY = "KEY_DIARY"
    }
}
