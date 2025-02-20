package com.teammeditalk.medicalconnect.ui.onboarding

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel
    @Inject
    constructor() : ViewModel() {
        private val _selectedLanguage = MutableStateFlow("")
        val selectedLanguage = _selectedLanguage.asStateFlow()

        // 1, 언어 선택
        fun setLanguage(selectedLanguage: String) {
            _selectedLanguage.value = selectedLanguage
            Timber.d("선택된 언어:${_selectedLanguage.value}")
        }

        // 2. 언어 저장
        fun saveUserLanguage() {
        }
        // 3. 온보딩에 선택한 개인 건강 정보 로컬에 저장하기

        fun saveUserHealthInfo() {
        }
    }
