package com.malibin.study.trying.mvvm.presentation.diary

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.malibin.study.trying.mvvm.data.DiaryMemory
import com.malibin.study.trying.mvvm.data.local.DiariesLocalSource
import com.malibin.study.trying.mvvm.data.local.db.DailyDiaryDatabase
import com.malibin.study.trying.mvvm.databinding.ActivityDiariesBinding
import com.malibin.study.trying.mvvm.domain.Diary
import com.malibin.study.trying.mvvm.presentation.diary.edit.EditDiaryActivity
import java.util.*

class DiariesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDiariesBinding
    private lateinit var diariesAdapter: DiariesAdapter

    private lateinit var editDiaryActivityLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        editDiaryActivityLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                onEditDiaryFinished(it)
            }
        initView()


        // Temporary code
        val diariesLocalSource = DiariesLocalSource(
            DailyDiaryDatabase.newInstance(this).getDiariesDao()
        )
        diariesLocalSource.saveDiary(Diary("id", "title", "content", Date())) {
            it
                .onSuccess {
                    Log.d("MalibinDebug", "save Success!")

                    diariesLocalSource.getDiary("id") { getResult ->
                        getResult
                            .onSuccess { diary -> Log.d("MalibinDebug", "diary : $diary") }
                            .onFailure { Log.d("MalibinDebug", "get Failure!") }
                    }
                }
                .onFailure { Log.d("MalibinDebug", "save Failure! ${it.stackTraceToString()}") }
        }
        // Temporary Code
    }

    override fun onResume() {
        super.onResume()

        diariesAdapter.submitList(DiaryMemory.getAllDiaries())
    }

    private fun initView() {
        binding = ActivityDiariesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.listDiaries.adapter = DiariesAdapter(::onDiaryClick).also { diariesAdapter = it }
        binding.buttonNewDiary.setOnClickListener { deployEditDiaryActivity() }
    }

    private fun onDiaryClick(diary: Diary) {
        deployEditDiaryActivity(diary)
    }

    private fun deployEditDiaryActivity(diary: Diary? = null) {
        val intent = Intent(this, EditDiaryActivity::class.java)
        if (diary != null) {
            intent.putExtra(EditDiaryActivity.KEY_DIARY, diary.id)
        }
        editDiaryActivityLauncher.launch(intent)
    }

    private fun onEditDiaryFinished(result: ActivityResult) = when (result.resultCode) {
        Activity.RESULT_OK -> showToast("작성 완료!")
        Activity.RESULT_CANCELED -> showToast("작성이 취소되었습니다.")
        else -> showToast("Unexpected Activity Result : $result")
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
