package com.teammeditalk.medicalconnect.ui.util

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.teammeditalk.medicalconnect.databinding.ExposedDropdownMenuHospitalTypeBinding

class HospitalTypeDropDownMenu(
    private val context: Context,
    private val attributeSet: AttributeSet,
) : ConstraintLayout(context, attributeSet) {
    private lateinit var binding: ExposedDropdownMenuHospitalTypeBinding

    init {
        initView()
    }

    private fun initView() {
        val inflater = LayoutInflater.from(context)
        binding = ExposedDropdownMenuHospitalTypeBinding.inflate(inflater, this, false)
        addView(binding.root)
    }
}
