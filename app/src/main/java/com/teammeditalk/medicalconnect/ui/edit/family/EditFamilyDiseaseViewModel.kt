package com.teammeditalk.medicalconnect.ui.edit.family

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
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditFamilyDiseaseViewModel
    @Inject
    constructor(
        @ApplicationContext val context: Context,
    ) : ViewModel() {
        private val _familyDiseaseList = MutableStateFlow(emptyList<String>())
        val familyDiseaseList = _familyDiseaseList.asStateFlow()

        init {
            getFamilyDiseaseInfo()
        }

        // todo : 가족력  정보 가져오기
        private fun getFamilyDiseaseInfo() {
            viewModelScope.launch {
                context.userHealthPreferencesStore.data.collectLatest {
                    Timber.d("")
                    _familyDiseaseList.value = it.familyDiseaseList
                }
            }
        }

        fun setFamilyDiseaseList(list: List<String>) {
            viewModelScope.launch {
                context.userHealthPreferencesStore.updateData {
                    it
                        .toBuilder()
                        .clearFamilyDisease()
                        .addAllFamilyDisease(list)
                        .build()
                }
            }
        }
    }
