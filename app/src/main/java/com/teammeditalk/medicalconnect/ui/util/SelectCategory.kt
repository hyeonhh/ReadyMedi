package com.teammeditalk.medicalconnect.ui.util

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.databinding.ItemSymptomBinding

class SelectCategory(
    private val context: Context,
    private val attributeSet: AttributeSet,
) : ConstraintLayout(context, attributeSet) {
    private var isSelected: Boolean = false
    private lateinit var binding: ItemSymptomBinding
    private lateinit var tvTitle: TextView
    private lateinit var tvContent: TextView

    init {
        initView()
        initAttrs()
    }

    private fun initView() {
        val layoutInflater = LayoutInflater.from(context)
        binding = ItemSymptomBinding.inflate(layoutInflater, this, false)
        tvTitle = binding.tvTitle
        tvContent = binding.tvContent
        addView(binding.root)
    }

    private fun initAttrs() {
        context.theme
            .obtainStyledAttributes(
                attributeSet,
                R.styleable.SelectCategory,
                0,
                0,
            ).apply {
                try {
                    isSelected = getBoolean(R.styleable.SelectCategory_isCategorySelected, false)
                    tvTitle.text = getString(R.styleable.SelectCategory_categoryTitle) ?: ""
                    tvContent.text = getString(R.styleable.SelectCategory_categoryContent) ?: ""
                } finally {
                    recycle()
                }
            }
    }
}
