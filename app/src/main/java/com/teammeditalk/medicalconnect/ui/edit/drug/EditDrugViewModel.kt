package com.teammeditalk.medicalconnect.ui.edit.drug

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
class EditDrugViewModel
    @Inject
    constructor(
        @ApplicationContext val context: Context,
    ) : ViewModel() {
        private val _drugList = MutableStateFlow(emptyList<String>())
        val drugList = _drugList.asStateFlow()

        private val _drugCount = MutableStateFlow(0)
        val drugCount = _drugCount.asStateFlow()

        private val _drugDuration = MutableStateFlow("")
        val drugDuration = _drugDuration.asStateFlow()

        private val _drugStartDate = MutableStateFlow("")
        val drugStartDate = _drugStartDate.asStateFlow()

        init {
            getDrugInfo()
        }

        // todo : 약  정보 가져오기
        private fun getDrugInfo() {
            viewModelScope.launch {
                context.userHealthPreferencesStore.data.collectLatest {
                    _drugList.value = it.drugInfoList
                    _drugDuration.value = it.duration
                    _drugCount.value = it.count
                }
            }
        }

        // 복용 중인 약물 저장
        fun saveUserDrug(drug: List<String>) {
            viewModelScope.launch {
                try {
                    context.userHealthPreferencesStore.updateData {
                        it
                            .toBuilder()
                            .clearDrugInfo()
                            .addAllDrugInfo(drug)
                            .build()
                    }
                    Timber.e("success to save drug :$drug")
                } catch (e: Exception) {
                    e.printStackTrace()
                    Timber.e("failed to save drug :${e.message}")
                }
            }
        }

        fun saveUserDrugDuration(duration: String) {
            viewModelScope.launch {
                try {
                    context.userHealthPreferencesStore.updateData {
                        it
                            .toBuilder()
                            .clearDuration()
                            .setDuration(duration)
                            .build()
                    }
                    Timber.d("success to save drug duration :$duration")
                } catch (e: Exception) {
                    e.printStackTrace()
                    Timber.e("failed to save drug duration :${e.message}")
                }
            }
        }

        fun saveUserDrugCount(count: String) {
            viewModelScope.launch {
                try {
                    context.userHealthPreferencesStore.updateData {
                        it
                            .toBuilder()
                            .clearCount()
                            .setCount(count.toInt())
                            .build()
                    }
                    Timber.d("success to save drug count :$count")
                } catch (e: Exception) {
                    e.printStackTrace()
                    Timber.e("failed to save drug count :${e.message}")
                }
            }
        }

        fun saveUserDrugStartDate(startDate: String) {
            viewModelScope.launch {
                try {
                    context.userHealthPreferencesStore.updateData {
                        it
                            .toBuilder()
                            .clearStartDate()
                            .setStartDate(startDate)
                            .build()
                    }
                    Timber.d("success to save drug start :$startDate")
                } catch (e: Exception) {
                    e.printStackTrace()
                    Timber.e("failed to save drug start :${e.message}")
                }
            }
        }
    }
