package com.malibin.study.trying.mvvm.presentation.memos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.malibin.study.trying.mvvm.databinding.ItemDiaryBinding
import com.malibin.study.trying.mvvm.domain.Diary

class DiariesAdapter(
    private val onDiaryClick: ((Diary) -> Unit)? = null
) : ListAdapter<Diary, DiariesAdapter.ViewHolder>(DiariesComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemDiaryBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val memo = getItem(position)
        holder.bind(memo, onDiaryClick)
    }

    class ViewHolder(
        private val binding: ItemDiaryBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(diary: Diary, onMemoClick: ((Diary) -> Unit)? = null) {
            binding.diary = diary
            binding.root.setOnClickListener { onMemoClick?.invoke(diary) }
        }
    }

    private class DiariesComparator : DiffUtil.ItemCallback<Diary>() {
        override fun areItemsTheSame(oldItem: Diary, newItem: Diary): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Diary, newItem: Diary): Boolean {
            return oldItem == newItem
        }
    }
}
