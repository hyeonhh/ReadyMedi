package com.teammeditalk.medicalconnect.ui.onboarding.language

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat

object LanguageUtil {
    fun setApplicationLocales(locale: LocaleListCompat) {
        AppCompatDelegate.setApplicationLocales(locale)
    }
}
