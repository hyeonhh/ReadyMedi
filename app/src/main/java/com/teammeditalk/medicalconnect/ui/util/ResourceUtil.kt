package com.teammeditalk.medicalconnect.ui.util

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

object ResourceUtil {
    private fun getResourceIdByName(
        context: Context,
        resourceName: String,
    ): Int =
        context.resources.getIdentifier(
            resourceName, // "symptom_cough_desc"
            "string", // 리소스 타입
            context.packageName,
        )

    fun getForeignString(
        context: Context,
        lang: String,
        resourceName: String,
    ): String {
        val configuration = Configuration(context.resources.configuration)
        val locale =
            when (lang) {
                "en" -> Locale.ENGLISH
                "zh" -> Locale.CHINESE
                "ja" -> Locale.JAPANESE
                "ko" -> Locale.KOREAN
                else -> Locale.ENGLISH
            }
        configuration.setLocale(locale)
        val langContext = context.createConfigurationContext(configuration)
        return langContext.getString(getResourceIdByName(context, resourceName))
    }

    fun getKoreanString(
        context: Context,
        resourceName: String,
    ): String {
        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(Locale.KOREAN)

        // 한국어 설정이 적용된 새 Context 생성
        val koreanContext = context.createConfigurationContext(configuration)

        // 이 Context로 한국어 문자열 가져오기
        return koreanContext.getString(getResourceIdByName(context, resourceName))
    }
}
