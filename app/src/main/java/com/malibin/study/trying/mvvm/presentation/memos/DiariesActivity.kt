package com.malibin.study.trying.mvvm.presentation.memos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.malibin.study.trying.mvvm.databinding.ActivityDiariesBinding
import com.malibin.study.trying.mvvm.domain.Diary
import java.util.*

class DiariesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDiariesBinding
    private lateinit var diariesAdapter: DiariesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDiariesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        diariesAdapter = DiariesAdapter()
        binding.listDiaries.adapter = diariesAdapter
        diariesAdapter.submitList(STUB_DIARIES)
    }

    private fun onMemoClick(diary: Diary) {

    }

    companion object {
        val STUB_DIARIES = listOf(
            Diary(0, "제목", "내용", Date()),
            Diary(1, "제목", "내용", Date()),
            Diary(2, "제목", "내용", Date()),
            Diary(3, "제목", "내용", Date()),
            Diary(4, "제목", "내용", Date()),
        )
    }
}
