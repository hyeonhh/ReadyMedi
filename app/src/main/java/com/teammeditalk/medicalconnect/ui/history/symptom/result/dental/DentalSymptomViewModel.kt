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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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

        private val _symptomContent = MutableStateFlow("")
        val symptomContent = _symptomContent.asStateFlow()

        private val _typeList = MutableStateFlow("")
        val typeList = _typeList.asStateFlow()

        private val _otherList = MutableStateFlow("")
        val otherList = _otherList.asStateFlow()

        private val _worseList = MutableStateFlow("")
        val worseList = _worseList.asStateFlow()

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

        private val _userHealthInfoByKorean = MutableStateFlow(HealthInfo())
        val userHealthInfoByKorean = _userHealthInfoByKorean.asStateFlow()

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

                                val typeListByKorean = mutableListOf<String>()
                                val otherListByKorean = mutableListOf<String>()
                                val worseListByKorean = mutableListOf<String>()

                                _dentalResponse.value = result

                                _symptomTitle.value = _dentalResponse.value.symptomTitle

                                _symptomContent.value =
                                    ResourceUtil.getForeignString(context, _userLanguage.value, _dentalResponse.value.symptomContent)

                                _symptomByKorean.value = ResourceUtil.getKoreanString(context, _dentalResponse.value.symptomContent)

                                _dentalResponse.value.otherSymptom.forEach {
                                    otherListByKorean.add(ResourceUtil.getKoreanString(context, it))
                                    otherList.add(ResourceUtil.getKoreanString(context, it))
                                }

                                _dentalResponse.value.worseList.forEach {
                                    worseListByKorean.add(ResourceUtil.getKoreanString(context, it))
                                    worseList.add(ResourceUtil.getKoreanString(context, it))
                                }
                                _dentalResponse.value.type.forEach {
                                    typeList.add(ResourceUtil.getForeignString(context, _userLanguage.value, it))
                                    typeListByKorean.add(ResourceUtil.getKoreanString(context, it))
                                }

                                _otherListByKorean.value = otherListByKorean.joinToString(", ")
                                _worseListByKorean.value = worseListByKorean.joinToString(", ")
                                _typeListByKorean.value = typeListByKorean.joinToString(", ")

                                _otherList.value = otherList.joinToString(", ")
                                _worseList.value = worseList.joinToString(", ")
                                _typeList.value = typeList.joinToString(", ")
                            }
                        }.addOnFailureListener {
                            it.printStackTrace()
                            Timber.d("failed to get dental data :${it.message}")
                        }
                }
        }
    }
