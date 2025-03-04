package com.teammeditalk.medicalconnect.ui.util

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.databinding.ExposedDropdownMenuUnselectedBinding

class ExposedDropdownMenu(
    private val context: Context,
    private val attributeSet: AttributeSet,
) : ConstraintLayout(context, attributeSet) {
    private lateinit var binding: ExposedDropdownMenuUnselectedBinding
    private lateinit var menuTitle: TextView

    init {
        initView()
        initAttrs()
    }

    fun getTvTitle(): String = menuTitle.text.toString()

    fun setTvTitle(title: String) {
        menuTitle.text = title
        menuTitle.setTextColor(R.color.black)
    }

    private fun initView() {
        val layoutInflater = LayoutInflater.from(context)
        binding = ExposedDropdownMenuUnselectedBinding.inflate(layoutInflater, this, false)
        menuTitle = binding.tvTitle
        addView(binding.root)
    }

    private fun initAttrs() {
        context.theme
            .obtainStyledAttributes(
                attributeSet,
                R.styleable.ExposedDropdownMenu,
                0,
                0,
            ).apply {
                try {
                    menuTitle.text = getString(R.styleable.ExposedDropdownMenu_menuTitle) ?: "선택"
                } finally {
                    recycle()
                }
            }
    }
}
