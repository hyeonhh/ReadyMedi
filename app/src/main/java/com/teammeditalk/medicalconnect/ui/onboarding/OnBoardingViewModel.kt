package com.teammeditalk.medicalconnect.ui.onboarding

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teammeditalk.medicalconnect.UserHealthInfo
import com.teammeditalk.medicalconnect.UserInfo
import com.teammeditalk.medicalconnect.data.serializer.UserHealthPreferencesSerializer
import com.teammeditalk.medicalconnect.data.serializer.UserPreferencesSerializer
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

private val Context.userHealthPreferencesStore: DataStore<UserHealthInfo> by dataStore(
    fileName = "user_prefs.pb",
    serializer = UserHealthPreferencesSerializer,
)

private val Context.userPreferencesStore: DataStore<UserInfo> by dataStore(
    fileName = "user_lang.pb",
    serializer = UserPreferencesSerializer,
)

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
                            .setLanguage(language)
                            .build()
                    }
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
                            .addAllDiseaseInfo(disease)
                            .build()
                    }
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
                            .addAllFamilyDisease(familyDisease)
                            .build()
                    }
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
                            .addAllAllergyInfo(allergy)
                            .build()
                    }
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
                            .addAllDrugInfo(drug)
                            .build()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Timber.e("failed to save drug :${e.message}")
                }
            }
        }
    }
