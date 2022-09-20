package com.malibin.study.trying.mvvm.presentation.diary.edit

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.malibin.study.trying.mvvm.R

class EditDiaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_edit_diary)

        findViewById<Button>(R.id.buttonSubmit).setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    companion object {
        const val KEY_DIARY = "KEY_DIARY"
        const val REQUEST_CODE = 100
    }
}
