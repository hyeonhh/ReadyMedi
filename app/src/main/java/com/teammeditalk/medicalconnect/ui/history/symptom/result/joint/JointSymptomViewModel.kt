package com.teammeditalk.medicalconnect.ui.history.symptom.result.joint

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.teammeditalk.medicalconnect.copy
import com.teammeditalk.medicalconnect.data.model.info.HealthInfo
import com.teammeditalk.medicalconnect.data.model.question.JointQuestionResponse
import com.teammeditalk.medicalconnect.data.serializer.UserHealthPreferencesSerializer.userHealthPreferencesStore
import com.teammeditalk.medicalconnect.data.serializer.UserPreferencesSerializer.userPreferencesStore
import com.teammeditalk.medicalconnect.ui.util.ResourceUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class JointSymptomViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        @ApplicationContext val context: Context,
    ) : ViewModel() {
        private val _jointResponse = MutableStateFlow(JointQuestionResponse())
        val jointResponse = _jointResponse.asStateFlow()

        private val _symptomTitle = MutableStateFlow("")
        val symptomTitle =
            _symptomTitle
                .map {
                    ResourceUtil.getForeignString(context, _userLanguage.value, it)
                }.stateIn(
                    scope = viewModelScope,
                    initialValue = "",
                    started = SharingStarted.Lazily,
                )

        val symptomTitleByKorean =
            _symptomTitle
                .map {
                    ResourceUtil.getKoreanString(context, it)
                }.stateIn(
                    scope = viewModelScope,
                    initialValue = "",
                    started = SharingStarted.Lazily,
                )

        private val _typeList = MutableStateFlow("")
        val typeList = _typeList.asStateFlow()

        private val _otherList = MutableStateFlow("")
        val otherList = _otherList.asStateFlow()

        private val _worseList = MutableStateFlow("")
        val worseList = _worseList.asStateFlow()

        private val _otherListByKorean = MutableStateFlow("")
        val otherListByKorean = _otherListByKorean.asStateFlow()

        private val _symptomContent = MutableStateFlow("")
        val symptomContent = _symptomContent.asStateFlow()

        private val _worseListByKorean = MutableStateFlow("")
        val worseListByKorean = _worseListByKorean.asStateFlow()

        private val _symptomByKorean = MutableStateFlow("")
        val symptomByKorean = _symptomByKorean.asStateFlow()

        private val _typeListByKorean = MutableStateFlow("")
        val typeListByKorean = _typeListByKorean.asStateFlow()

        private val _userLanguage = MutableStateFlow("")
        val userLanguage = _userLanguage.asStateFlow()

        //   유저 건강 정보
        private val _userHealthInfo = MutableStateFlow(HealthInfo())
        val userHealthInfo = _userHealthInfo.asStateFlow()

        private val _userHealthInfoByKorean = MutableStateFlow(HealthInfo())
        val userHealthInfoByKorean = _userHealthInfoByKorean.asStateFlow()

        // 언어 불러오기
        private fun getUserLanguage() {
            viewModelScope.launch {
                context.userPreferencesStore.data.collectLatest {
                    _userLanguage.value = it.language
                }
            }
        }

        private fun getUserHealthInfo() {
            viewModelScope.launch {
                context.userHealthPreferencesStore.data.collectLatest {
                    Timber.d("건강 데이터 :$it")
                    val diseaseList = mutableListOf<String>()
                    val familyDiseaseList = mutableListOf<String>()
                    val allergyList = mutableListOf<String>()
                    val drugList = mutableListOf<String>()

                    val diseaseListByKorean = mutableListOf<String>()
                    val familyDiseaseListByKorean = mutableListOf<String>()
                    val allergyListByKorean = mutableListOf<String>()
                    val drugListByKorean = mutableListOf<String>()

                    if (it.diseaseInfoList.isNotEmpty()) {
                        it.diseaseInfoList.forEach {
                            diseaseList.add(
                                ResourceUtil.getForeignString(
                                    context,
                                    _userLanguage.value,
                                    it,
                                ),
                            )
                            diseaseListByKorean.add(
                                ResourceUtil.getKoreanString(
                                    context,
                                    it,
                                ),
                            )
                        }
                    }
                    if (it.familyDiseaseList.isNotEmpty()) {
                        it.familyDiseaseList.forEach {
                            familyDiseaseList.add(
                                ResourceUtil.getForeignString(
                                    context,
                                    _userLanguage.value,
                                    it,
                                ),
                            )
                            familyDiseaseListByKorean.add(
                                ResourceUtil.getKoreanString(
                                    context,
                                    it,
                                ),
                            )
                        }
                    }

                    if (it.allergyInfoList.isNotEmpty()) {
                        it.allergyInfoList.forEach {
                            allergyList.add(
                                ResourceUtil.getForeignString(
                                    context,
                                    _userLanguage.value,
                                    it,
                                ),
                            )
                            allergyListByKorean.add(
                                ResourceUtil.getKoreanString(
                                    context,
                                    it,
                                ),
                            )
                        }
                    }

                    if (it.drugInfoList.isNotEmpty()) {
                        it.drugInfoList.forEach {
                            drugList.add(
                                ResourceUtil.getForeignString(
                                    context,
                                    _userLanguage.value,
                                    it,
                                ),
                            )
                            drugListByKorean.add(
                                ResourceUtil.getKoreanString(
                                    context,
                                    it,
                                ),
                            )
                        }
                    }
                    _userHealthInfo.value =
                        _userHealthInfo.value.copy(
                            diseaseList = diseaseList,
                            familyDiseaseList = familyDiseaseList,
                            allergyList = allergyList,
                            drugList = drugList,
                            drugTakingDuration = it.duration,
                            drugTakingCount = it.count,
                            drugStartDate = it.startDate,
                        )
                    _userHealthInfoByKorean.value =
                        _userHealthInfoByKorean.value.copy(
                            diseaseList = diseaseListByKorean,
                            familyDiseaseList = familyDiseaseListByKorean,
                            allergyList = allergyListByKorean,
                            drugList = drugListByKorean,
                            drugTakingDuration = it.duration,
                            drugTakingCount = it.count,
                            drugStartDate = it.startDate,
                        )
                }
            }
        }

        // Safe Args로 전달된 ID를 바로 가져옵니다
        private val timeStamp: String =
            savedStateHandle.get<String>("timeStamp")
                ?: throw IllegalArgumentException("time stamp가 필요합니다!")

        private val uid: String =
            savedStateHandle.get<String>("uid")
                ?: throw IllegalArgumentException("uid가 필요합니다!")

        init {
            getSymptom()
            getUserLanguage()
            getUserHealthInfo()
        }

        fun getSymptom() {
            val db = Firebase.firestore
            db
                .collection("symptom_result_$uid")
                .document(timeStamp)
                .get()
                .addOnSuccessListener {
                    val result =
                        it.toObject(JointQuestionResponse::class.java)
                    if (result != null) {
                        _jointResponse.value = result

                        val typeList = mutableListOf<String>()
                        val otherList = mutableListOf<String>()
                        val worseList = mutableListOf<String>()

                        val typeListByKorean = mutableListOf<String>()
                        val otherListByKorean = mutableListOf<String>()
                        val worseListByKorean = mutableListOf<String>()

                        _symptomTitle.value = _jointResponse.value.symptomTitle

                        _symptomContent.value =
                            ResourceUtil.getForeignString(context, _userLanguage.value, _jointResponse.value.symptomContent)
                        _symptomByKorean.value = ResourceUtil.getKoreanString(context, _jointResponse.value.symptomContent)
                        _jointResponse.value.type.forEach {
                            typeList.add(ResourceUtil.getForeignString(context, _userLanguage.value, it))
                            typeListByKorean.add(ResourceUtil.getKoreanString(context, it))
                        }
                        _jointResponse.value.worseList.forEach {
                            worseList.add(ResourceUtil.getForeignString(context, _userLanguage.value, it))
                            worseListByKorean.add(ResourceUtil.getKoreanString(context, it))
                        }
                        _jointResponse.value.otherSymptom.forEach {
                            otherList.add(ResourceUtil.getForeignString(context, _userLanguage.value, it))
                            otherListByKorean.add(ResourceUtil.getKoreanString(context, it))
                        }
                        _typeList.value = typeList.joinToString(", ")
                        _worseList.value = worseList.joinToString(", ")
                        _otherList.value = otherList.joinToString(", ")

                        _typeListByKorean.value = typeListByKorean.joinToString(", ")
                        _otherListByKorean.value = otherListByKorean.joinToString(", ")
                        _worseListByKorean.value = worseListByKorean.joinToString(", ")
                    }
                }.addOnFailureListener {
                    it.printStackTrace()
                    Timber.d("failed to get dental data :${it.message}")
                }
        }
    }
