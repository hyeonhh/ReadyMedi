package com.teammeditalk.medicalconnect.ui.util

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.teammeditalk.medicalconnect.databinding.TextInput3sentenceBinding

class TextInput3Sentence(
    private val context: Context,
    private val attributeSet: AttributeSet,
) : ConstraintLayout(context, attributeSet) {
    private lateinit var binding: TextInput3sentenceBinding

    init {
        initView()
    }

    private fun initView() {
        val layoutInflater = LayoutInflater.from(context)
        binding = TextInput3sentenceBinding.inflate(layoutInflater, this, false)
        addView(binding.root)
    }
}
