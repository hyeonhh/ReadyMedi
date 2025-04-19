package com.teammeditalk.medicalconnect.ui.history.symptom.result.dental

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.teammeditalk.medicalconnect.data.model.info.HealthInfo
import com.teammeditalk.medicalconnect.data.model.question.DentalQuestionResponse
import com.teammeditalk.medicalconnect.data.serializer.UserHealthPreferencesSerializer.userHealthPreferencesStore
import com.teammeditalk.medicalconnect.data.serializer.UserPreferencesSerializer.userPreferencesStore
import com.teammeditalk.medicalconnect.ui.util.ResourceUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DentalSymptomViewModel
    @Inject
    constructor(
        @ApplicationContext val context: Context,
        saveStateHandle: SavedStateHandle,
    ) : ViewModel() {
        private val uid: String = checkNotNull(saveStateHandle["uid"])
        private val timeStamp: String = checkNotNull(saveStateHandle["timeStamp"])

        init {
            getUserHealthInfo()
            getUserLanguage()
            getSymptom()
        }

        private val _typeListByKorean = MutableStateFlow("")
        private val _symptomByKorean = MutableStateFlow("")
        private val _worseListByKorean = MutableStateFlow("")
        private val _otherListByKorean = MutableStateFlow("")

        val typeListByKorean = _typeListByKorean.asStateFlow()
        val symptomByKorean = _symptomByKorean.asStateFlow()
        val worseListByKorean = _worseListByKorean.asStateFlow()
        val otherListByKorean = _otherListByKorean.asStateFlow()

        private val _userLanguage = MutableStateFlow("")

        val userLanguage = _userLanguage.asStateFlow()

        //   유저 건강 정보
        private val _userHealthInfo = MutableStateFlow(HealthInfo())
        val userHealthInfo = _userHealthInfo.asStateFlow()

        private val _dentalResponse = MutableStateFlow(DentalQuestionResponse())
        val dentalResponse = _dentalResponse.asStateFlow()

        // 언어 불러오기
        private fun getUserLanguage() {
            viewModelScope.launch {
                context.userPreferencesStore.data.collectLatest {
                    _userLanguage.value = it.language
                }
            }
        }

        // 로컬에 저장된 내용 불러오기
        private fun getUserHealthInfo() {
            viewModelScope.launch {
                context.userHealthPreferencesStore.data.collectLatest {
                    _userHealthInfo.value =
                        _userHealthInfo.value.copy(
                            diseaseList = it.diseaseInfoList,
                            familyDiseaseList = it.familyDiseaseList,
                            allergyList = it.allergyInfoList,
                            drugList = it.drugInfoList,
                            drugTakingDuration = it.duration,
                            drugTakingCount = it.count,
                        )
                }
            }
        }

        private fun getSymptom() {
            viewModelScope
                .launch {
                    val db = Firebase.firestore
                    db
                        .collection("symptom_result_$uid")
                        .document(timeStamp)
                        .get()
                        .addOnSuccessListener {
                            val result =
                                it.toObject(DentalQuestionResponse::class.java)

                            if (result != null) {
                                val otherList = mutableListOf<String>()
                                val typeList = mutableListOf<String>()
                                val worseList = mutableListOf<String>()

                                _dentalResponse.value = result
                                _symptomByKorean.value = ResourceUtil.getKoreanString(context, _dentalResponse.value.symptomContent)
                                _dentalResponse.value.otherSymptom.forEach {
                                    otherList.add(ResourceUtil.getKoreanString(context, it))
                                }

                                _dentalResponse.value.worseList.forEach {
                                    worseList.add(ResourceUtil.getKoreanString(context, it))
                                }
                                _dentalResponse.value.type.forEach {
                                    typeList.add(ResourceUtil.getKoreanString(context, it))
                                }

                                _otherListByKorean.value = otherList.joinToString(", ")
                                _worseListByKorean.value = worseList.joinToString(", ")
                                _typeListByKorean.value = typeList.joinToString(", ")
                            }
                        }.addOnFailureListener {
                            it.printStackTrace()
                            Timber.d("failed to get dental data :${it.message}")
                        }
                }
        }
    }
