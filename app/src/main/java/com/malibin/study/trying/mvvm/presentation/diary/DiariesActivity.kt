package com.malibin.study.trying.mvvm.presentation.diary

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.malibin.study.trying.mvvm.data.local.db.DailyDiaryDatabase
import com.malibin.study.trying.mvvm.data.local.mapper.LocalDiariesMapper
import com.malibin.study.trying.mvvm.data.local.source.DiariesLocalSource
import com.malibin.study.trying.mvvm.data.remote.mapper.RemoteDiariesMapper
import com.malibin.study.trying.mvvm.data.remote.service.MalibinService
import com.malibin.study.trying.mvvm.data.remote.source.DiariesRemoteSource
import com.malibin.study.trying.mvvm.data.repository.RealDiariesRepository
import com.malibin.study.trying.mvvm.databinding.ActivityDiariesBinding
import com.malibin.study.trying.mvvm.domain.Diary
import com.malibin.study.trying.mvvm.presentation.diary.edit.EditDiaryActivity

class DiariesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDiariesBinding
    private lateinit var diariesAdapter: DiariesAdapter

    private lateinit var editDiaryActivityLauncher: ActivityResultLauncher<Intent>

    private val diariesViewModel: DiariesViewModel by viewModels { DiariesViewModelFactory(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        editDiaryActivityLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                onEditDiaryFinished(it)
            }
        initView()

        diariesViewModel.diaries.observe(this) {
            diariesAdapter.submitList(it)
        }
    }

    override fun onResume() {
        super.onResume()
        diariesViewModel.loadDiaries()
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

    class DiariesViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return when (modelClass) {
                DiariesViewModel::class.java -> {
                    DiariesViewModel(
                        RealDiariesRepository(
                            DiariesLocalSource(
                                DailyDiaryDatabase.getInstance(context).getDiariesDao(),
                                LocalDiariesMapper(),
                            ),
                            DiariesRemoteSource(
                                MalibinService.getInstance(),
                                RemoteDiariesMapper(),
                            ),
                        )
                    )
                }
                else -> throw IllegalArgumentException("$modelClass cannot create in DiariesViewModelFactory")
            } as T
        }
    }
}
