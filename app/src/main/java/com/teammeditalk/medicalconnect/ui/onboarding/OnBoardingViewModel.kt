package com.teammeditalk.medicalconnect.ui.onboarding

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

class OnBoardingViewModel : ViewModel() {
    private val _selectedLanguage = MutableStateFlow("")
    val selectedLanguage = _selectedLanguage.asStateFlow()

    // 1, 언어 선택
    fun setLanguage(selectedLanguage: String) {
        _selectedLanguage.value = selectedLanguage
        Timber.d("선택된 언어:${_selectedLanguage.value}")
    }

    // 2. wl
}
