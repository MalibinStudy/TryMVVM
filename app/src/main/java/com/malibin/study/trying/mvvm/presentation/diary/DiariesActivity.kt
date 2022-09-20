package com.malibin.study.trying.mvvm.presentation.diary

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.malibin.study.trying.mvvm.R
import com.malibin.study.trying.mvvm.domain.Diary
import java.util.*

class DiariesActivity : AppCompatActivity() {

    private lateinit var diariesAdapter: DiariesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


    }

    private fun initView() {
        setContentView(R.layout.activity_diaries)

        val writeDiaryButton = findViewById<Button>(R.id.buttonNewDiary)
        writeDiaryButton.setOnClickListener { deployEditDiaryActivity() }

        findViewById<RecyclerView>(R.id.listDiaries).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = DiariesAdapter(STUB_DIARIES) { onDiaryClick(it) }
                .also { diariesAdapter = it }
        }
    }

    private fun onDiaryClick(diary: Diary) {
        deployEditDiaryActivity(diary)
    }

    private fun deployEditDiaryActivity(diary: Diary? = null) {

    }

    private fun onEditMemoFinished(resultCode: Int) = when (resultCode) {
        Activity.RESULT_OK -> showToast("작성 완료!")
        Activity.RESULT_CANCELED -> showToast("작성이 취소되었습니다.")
        else -> showToast("Unexpected Activity Result : $resultCode")
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        val STUB_DIARIES = listOf(
            Diary("0", "제목", "내용", Date()),
            Diary("1", "제목", "내용", Date()),
            Diary("2", "제목", "내용", Date()),
            Diary("3", "제목", "내용", Date()),
            Diary("4", "제목", "내용", Date()),
        )
    }
}
