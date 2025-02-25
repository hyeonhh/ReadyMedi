package com.teammeditalk.medicalconnect.ui.util

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.databinding.CustomSelectBoxUnselectedBinding

class SelectBox(
    private val context: Context,
    private val attributeSet: AttributeSet,
) : ConstraintLayout(context, attributeSet) {
    private var isSelected: Boolean = false
    private lateinit var binding: CustomSelectBoxUnselectedBinding
    private lateinit var tvLanguage: TextView

    init {
        initView()
        initAttrs()
    }

    fun updateSelected(selected: Boolean) {
        isSelected = selected
        onClick(selected)
    }

    // 뷰 설정
    private fun initView() {
        val layoutInflater = LayoutInflater.from(context)
        binding = CustomSelectBoxUnselectedBinding.inflate(layoutInflater, this, false)
        tvLanguage = binding.tvLanguage
        addView(binding.root)
    }

    // 이벤트 설정
    private fun onClick(isSelected: Boolean) {
        context.theme
            .obtainStyledAttributes(
                attributeSet,
                R.styleable.SelectBox,
                0,
                0,
            ).apply {
                if (isSelected) {
                    val textColor =
                        getColor(
                            R.styleable.SelectBox_textColor,
                            context.getColor(R.color.blue50),
                        )
                    tvLanguage.setTextColor(textColor)
                    tvLanguage.setTextAppearance(R.style.Body2_Normal)
                    binding.layoutSelectBox.setBackgroundResource(R.drawable.shape_blue_rectangle_12dp_selected)
                    binding.icCheck.visibility = View.VISIBLE
                } else {
                    val textColor =
                        getColor(
                            R.styleable.SelectBox_textColor,
                            context.getColor(R.color.gray50),
                        )
                    binding.tvLanguage.setTextColor(textColor)
                    tvLanguage.setTextAppearance(R.style.Body2_Normal)
                    binding.layoutSelectBox.setBackgroundResource(R.drawable.shape_rectangle_12dp_unselected)
                    binding.icCheck.visibility = View.GONE
                }
            }
    }

    private fun initAttrs() {
        context.theme
            .obtainStyledAttributes(
                attributeSet,
                R.styleable.SelectBox,
                0,
                0,
            ).apply {
                try {
                    isSelected = getBoolean(R.styleable.SelectBox_isSelected, false)
                    tvLanguage.text = getString(R.styleable.SelectBox_language) ?: "선택 전"
                } finally {
                    recycle()
                }
            }
    }
}
