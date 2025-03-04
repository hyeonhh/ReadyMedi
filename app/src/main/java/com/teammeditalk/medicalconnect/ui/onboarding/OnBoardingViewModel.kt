package com.teammeditalk.medicalconnect.ui.onboarding

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teammeditalk.medicalconnect.data.serializer.UserHealthPreferencesSerializer.userHealthPreferencesStore
import com.teammeditalk.medicalconnect.data.serializer.UserPreferencesSerializer.userPreferencesStore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) : ViewModel() {
        // 2. 언어 저장
        fun saveUserLanguage(language: String) {
            viewModelScope.launch {
                try {
                    context.userPreferencesStore.updateData {
                        it
                            .toBuilder()
                            .clearLanguage()
                            .setLanguage(language)
                            .build()
                    }
                    Timber.d("success to save language")
                } catch (e: Exception) {
                    e.printStackTrace()
                    Timber.e("failed to save language :${e.message}")
                }
            }
        }

        // 3. 질병 저장
        fun saveUserDisease(disease: List<String>) {
            viewModelScope.launch {
                try {
                    context.userHealthPreferencesStore.updateData {
                        it
                            .toBuilder()
                            .clearDiseaseInfo()
                            .addAllDiseaseInfo(disease)
                            .build()
                    }
                    Timber.d("success to save user disease $disease")
                } catch (e: Exception) {
                    e.printStackTrace()
                    Timber.d("failed to save user disease ${e.message}")
                }
            }
        }

        // 가족 질병 저장
        fun saveUserFamilyDisease(familyDisease: List<String>) {
            viewModelScope.launch {
                try {
                    context.userHealthPreferencesStore.updateData {
                        it
                            .toBuilder()
                            .clearFamilyDisease()
                            .addAllFamilyDisease(familyDisease)
                            .build()
                    }
                    Timber.d("success to save family disease $familyDisease")
                } catch (e: Exception) {
                    e.printStackTrace()
                    Timber.d("failed to save user family disease :${e.message}")
                }
            }
        }

        // 알레르기 저장
        fun saveUserAllergy(allergy: List<String>) {
            viewModelScope.launch {
                try {
                    context.userHealthPreferencesStore.updateData {
                        it
                            .toBuilder()
                            .clearAllergyInfo()
                            .addAllAllergyInfo(allergy)
                            .build()
                    }
                    Timber.e("success to save allergy :$allergy")
                } catch (e: Exception) {
                    e.printStackTrace()
                    Timber.e("failed to save allergy :${e.message}")
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
