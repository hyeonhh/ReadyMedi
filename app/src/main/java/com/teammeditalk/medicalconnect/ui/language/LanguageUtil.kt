package com.teammeditalk.medicalconnect.ui.language

import android.content.Context
import java.util.Locale

object LanguageUtil {
    fun setLocale(
        context: Context,
        languageCode: String,
    ) {
        // 1. Locale 설정
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        // 2. Configuration 업데이트
        val config = context.resources.configuration
        config.setLocale(locale)

        // 3. Context 업데이트
        context.createConfigurationContext(config)

        // 4. resources 업데이트

        context.resources.updateConfiguration(
            config,
            context.resources.displayMetrics,
        )
        // 5. 언어 설정 저장
    }

    fun saveLanguagePreference(languageCode: String) {
    }
}
