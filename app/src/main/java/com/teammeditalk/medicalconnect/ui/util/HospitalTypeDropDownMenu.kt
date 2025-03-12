package com.teammeditalk.medicalconnect.ui.util

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.databinding.ExposedDropdownMenuHospitalTypeBinding

class HospitalTypeDropDownMenu(
    private val context: Context,
    private val attributeSet: AttributeSet,
) : ConstraintLayout(context, attributeSet) {
    private lateinit var binding: ExposedDropdownMenuHospitalTypeBinding

    init {
        initView()
    }

    fun setIsSelected(selected: Boolean) {
        if (selected) {
            binding.tvTitle.setTextColor(resources.getColor(R.color.white))
            binding.icCheck.setImageResource(R.drawable.ic_down_white)
        } else {
            binding.tvTitle.setTextColor(resources.getColor(R.color.gray70))
            binding.icCheck.setImageResource(R.drawable.ic_down_16dp)
        }
    }

    fun setText(text: String) {
        binding.tvTitle.text = text
    }

    private fun initView() {
        val inflater = LayoutInflater.from(context)
        binding = ExposedDropdownMenuHospitalTypeBinding.inflate(inflater, this, false)
        addView(binding.root)
    }
}
