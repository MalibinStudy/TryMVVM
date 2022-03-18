package com.malibin.study.trying.mvvm.presentation.diary.edit

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.malibin.study.trying.mvvm.data.DiaryMemory
import com.malibin.study.trying.mvvm.databinding.ActivityEditDiaryBinding
import com.malibin.study.trying.mvvm.domain.Diary
import java.util.*

class EditDiaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditDiaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadDiary(getDiaryId())

        binding.buttonSubmit.setOnClickListener {
            saveDiary()
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    private fun loadDiary(diaryId: String?) {
        val diary = DiaryMemory.getDiary(diaryId ?: return)
        binding.diary = diary
    }

    private fun getDiaryId(): String? = intent.getStringExtra(KEY_DIARY)

    private fun saveDiary() {
        val diary = Diary(
            id = UUID.randomUUID().toString(),
            title = binding.textTitle.text.toString(),
            content = binding.textContent.text.toString(),
            createDate = Date(),
        )
        DiaryMemory.saveDiary(diary)
    }

    companion object {
        const val KEY_DIARY = "KEY_DIARY"
    }
}
