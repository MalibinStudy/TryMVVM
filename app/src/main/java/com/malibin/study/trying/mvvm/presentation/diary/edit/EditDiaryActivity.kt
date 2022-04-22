package com.malibin.study.trying.mvvm.presentation.diary.edit

import android.app.Activity
import android.content.Context
import android.os.Bundle
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
import com.malibin.study.trying.mvvm.databinding.ActivityEditDiaryBinding

class EditDiaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditDiaryBinding

    private val editDiaryViewModel: EditDiaryViewModel by viewModels { EditViewModelFactory(this) }

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

    class EditViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return when (modelClass) {
                EditDiaryViewModel::class.java -> {
                    EditDiaryViewModel(
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
                else -> throw IllegalArgumentException("$modelClass cannot create in EditViewModelFactory")
            } as T
        }
    }
}
