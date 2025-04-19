package com.teammeditalk.medicalconnect.ui.util

import android.content.Context
import android.content.res.Configuration
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.databinding.CustomSelectBoxUnselectedBinding
import java.util.Locale

class SelectBox(
    private val context: Context,
    private val attributeSet: AttributeSet,
) : ConstraintLayout(context, attributeSet) {
    private var isSelected: Boolean = false
    private lateinit var binding: CustomSelectBoxUnselectedBinding
    private lateinit var tvContent: TextView

    init {
        initView()
        initAttrs()
    }

    private fun getResourceIdByName(resourceName: String): Int =
        context.resources.getIdentifier(
            resourceName, // "symptom_cough_desc"
            "string", // 리소스 타입
            context.packageName,
        )

    fun getKoreanString(resourceName: String): String {
        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(Locale.KOREAN)

        // 한국어 설정이 적용된 새 Context 생성
        val koreanContext = context.createConfigurationContext(configuration)

        // 이 Context로 한국어 문자열 가져오기
        return koreanContext.getString(getResourceIdByName(resourceName))
    }

    fun updateSelected(selected: Boolean) {
        isSelected = selected
        onClick(selected)
    }

    fun getContent(): String = binding.tvContent.text.toString()

    // 뷰 설정
    private fun initView() {
        val layoutInflater = LayoutInflater.from(context)
        binding = CustomSelectBoxUnselectedBinding.inflate(layoutInflater, this, false)
        tvContent = binding.tvContent
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
                    tvContent.setTextColor(textColor)
                    tvContent.setTextAppearance(R.style.Body2_Normal)
                    binding.layoutSelectBox.setBackgroundResource(R.drawable.shape_blue_rectangle_12dp_selected)
                    binding.icCheck.visibility = View.VISIBLE
                } else {
                    val textColor =
                        getColor(
                            R.styleable.SelectBox_textColor,
                            context.getColor(R.color.gray70),
                        )
                    binding.tvContent.setTextColor(textColor)
                    tvContent.setTextAppearance(R.style.Body2_Normal)
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
                    tvContent.text = getString(R.styleable.SelectBox_boxContent) ?: "선택 전"
                } finally {
                    recycle()
                }
            }
    }
}
