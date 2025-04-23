package com.teammeditalk.medicalconnect.ui.util

import android.content.Context
import android.content.res.Configuration
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.databinding.ItemSymptomBinding
import org.apache.commons.compress.harmony.archive.internal.nls.Messages.getString
import java.util.Locale

class SelectCategory(
    private val context: Context,
    private val attributeSet: AttributeSet,
) : ConstraintLayout(context, attributeSet) {
    private var isSelected: Boolean = false
    private lateinit var binding: ItemSymptomBinding
    private lateinit var tvTitle: TextView
    private lateinit var tvContent: TextView

    private lateinit var titleTag: String

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

    fun getTitleId(): String = titleTag

    fun getContent(): Pair<String, String> =
        tvTitle.text.toString() to
            tvContent.text.toString()

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
                    titleTag = getString(R.styleable.SelectCategory_titleTag) ?: ""
                } finally {
                    recycle()
                }
            }
    }
}
