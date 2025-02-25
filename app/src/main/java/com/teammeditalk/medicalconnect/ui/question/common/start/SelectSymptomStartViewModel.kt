package com.teammeditalk.medicalconnect.ui.question.common.start

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SelectSymptomStartViewModel
    @Inject
    constructor() : ViewModel() {
        private val _selectedDate = MutableStateFlow("")
        val selectedDate = _selectedDate.asStateFlow()

        fun setSymptomStartDate(selectedDate: String) {
            _selectedDate.value = selectedDate
        }
    }
