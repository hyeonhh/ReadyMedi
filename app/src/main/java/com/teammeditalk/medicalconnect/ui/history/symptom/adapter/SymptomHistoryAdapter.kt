package com.teammeditalk.medicalconnect.ui.history.symptom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teammeditalk.medicalconnect.data.model.history.SymptomHistory
import com.teammeditalk.medicalconnect.databinding.ItemSymptomHistoryBinding
import timber.log.Timber

class SymptomHistoryAdapter : RecyclerView.Adapter<SymptomHistoryAdapter.SymptomHistoryViewHolder>() {
    private val list: MutableList<SymptomHistory> = mutableListOf()

    fun setList(newList: List<SymptomHistory>) {
        list.addAll(newList)
        Timber.d("list :$list")
        notifyDataSetChanged()
    }

    inner class SymptomHistoryViewHolder(
        val binding: ItemSymptomHistoryBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: SymptomHistory) {
            Timber.d("bind :$data")
            binding.tvSymptomTitle.text = data.symptomTitle
            binding.tvSymptomContent.text = data.symptomContent
            binding.tvDate.text = data.timeStamp
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SymptomHistoryViewHolder {
        val binding = ItemSymptomHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SymptomHistoryViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(
        holder: SymptomHistoryViewHolder,
        position: Int,
    ) {
        holder.bind(list[position])
    }
}
