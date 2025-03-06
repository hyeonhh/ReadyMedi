package com.teammeditalk.medicalconnect.ui.edit

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
class EditDiseaseViewModel
    @Inject
    constructor(
        @ApplicationContext val context: Context,
    ) : ViewModel() {
        private val _diseaseList = MutableStateFlow(emptyList<String>())
        val diseaseList = _diseaseList.asStateFlow()

        init {
            viewModelScope.launch {
                context.userHealthPreferencesStore.data.collectLatest {
                    _diseaseList.value = it.diseaseInfoList
                }
            }
        }

        fun editDisease(editedList: List<String>) {
            viewModelScope.launch {
                context.userHealthPreferencesStore.updateData {
                    it
                        .toBuilder()
                        .clearDiseaseInfo()
                        .addAllDiseaseInfo(editedList)
                        .build()
                }
            }
        }
    }
