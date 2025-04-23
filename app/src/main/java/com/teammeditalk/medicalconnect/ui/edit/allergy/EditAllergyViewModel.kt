package com.teammeditalk.medicalconnect.ui.edit.allergy

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teammeditalk.medicalconnect.data.serializer.UserHealthPreferencesSerializer.userHealthPreferencesStore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditAllergyViewModel
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) : ViewModel() {
        private val _allergyList = MutableStateFlow(emptyList<String>())
        val allergyList = _allergyList.asStateFlow()

        init {
            getAllergyInfo()
        }

        // todo : 알레르기 정보 가져오기
        private fun getAllergyInfo() {
            viewModelScope.launch {
                context.userHealthPreferencesStore.data.collectLatest {
                    _allergyList.value = it.allergyInfoList
                }
            }
        }

        // todo :알레르기 정보 변경하기
        fun setAllergyInfo(allergyList: List<String>) {
            viewModelScope.launch {
                context.userHealthPreferencesStore.updateData {
                    it
                        .toBuilder()
                        .clearAllergyInfo()
                        .addAllAllergyInfo(allergyList)
                        .build()
                }
            }
        }
    }
