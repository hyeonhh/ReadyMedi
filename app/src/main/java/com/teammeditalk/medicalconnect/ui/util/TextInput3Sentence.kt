package com.teammeditalk.medicalconnect.ui.util

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import com.teammeditalk.medicalconnect.databinding.TextInput3sentenceBinding

class TextInput3Sentence(
    private val context: Context,
    private val attributeSet: AttributeSet,
) : ConstraintLayout(context, attributeSet) {
    private lateinit var binding: TextInput3sentenceBinding

    init {
        initView()
    }

    fun getContent(): String = binding.tvCount.text.toString()

    fun getTextWatcher() {
        binding.editText.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int,
                ) {
                    val textCount =
                        binding.editText.text.length
                            .toString()
                    binding.tvCount.text = textCount
                }

                override fun afterTextChanged(s: Editable?) {
                }
            },
        )
    }

    // todo : 텍스트 수 전달하기

    private fun initView() {
        val layoutInflater = LayoutInflater.from(context)
        binding = TextInput3sentenceBinding.inflate(layoutInflater, this, false)
        addView(binding.root)

        getTextWatcher()
    }
}
