package com.malibin.study.trying.mvvm.presentation.diary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.malibin.study.trying.mvvm.R
import com.malibin.study.trying.mvvm.domain.Diary
import java.text.SimpleDateFormat
import java.util.*

class DiariesAdapter(
    initialDiaries: List<Diary>,
    private val onDiaryClick: ((Diary) -> Unit) = {},
) : RecyclerView.Adapter<DiariesAdapter.ViewHolder>() {

    private val diaries: MutableList<Diary> = initialDiaries.toMutableList()

    override fun getItemCount(): Int = diaries.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val rootView = layoutInflater.inflate(R.layout.item_diary, parent, false)
        return ViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val diary = diaries[position]
        holder.bind(diary, onDiaryClick)
    }

    class ViewHolder(
        private val rootView: View,
        private val textTitle: TextView,
        private val textContext: TextView,
        private val textCreateDate: TextView,
    ) : RecyclerView.ViewHolder(rootView) {

        constructor(rootView: View) : this(
            rootView,
            rootView.findViewById(R.id.textTitle),
            rootView.findViewById(R.id.textContent),
            rootView.findViewById(R.id.textCreateDate),
        )

        fun bind(diary: Diary, onDiaryClick: ((Diary) -> Unit)? = null) {
            rootView.setOnClickListener { onDiaryClick?.invoke(diary) }
            textTitle.text = diary.title
            textContext.text = diary.content
            textCreateDate.text =
                SimpleDateFormat("yyyy.MM.dd", Locale.KOREA).format(diary.createDate)
        }
    }
}
