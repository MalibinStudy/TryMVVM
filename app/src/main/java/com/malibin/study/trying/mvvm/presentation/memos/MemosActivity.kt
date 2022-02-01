package com.malibin.study.trying.mvvm.presentation.memos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.malibin.study.trying.mvvm.databinding.ActivityMemosBinding
import com.malibin.study.trying.mvvm.domain.Memo
import java.util.*

class MemosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMemosBinding
    private lateinit var memosAdapter: MemosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMemosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        memosAdapter = MemosAdapter()
        binding.listMemos.adapter = memosAdapter
        memosAdapter.submitList(STUB_MEMOS)
    }

    companion object {
        val STUB_MEMOS = listOf(
            Memo(0, "제목", "내용", Date()),
            Memo(1, "제목", "내용", Date()),
            Memo(2, "제목", "내용", Date()),
            Memo(3, "제목", "내용", Date()),
            Memo(4, "제목", "내용", Date()),
        )
    }
}
