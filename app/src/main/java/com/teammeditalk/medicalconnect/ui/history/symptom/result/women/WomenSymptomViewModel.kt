package com.teammeditalk.medicalconnect.ui.history.symptom.result.women

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.teammeditalk.medicalconnect.data.model.info.HealthInfo
import com.teammeditalk.medicalconnect.data.model.question.WomenQuestionResponse
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
class WomenSymptomViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        @ApplicationContext val context: Context,
    ) : ViewModel() {
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

        private val _pregnancyCheck = MutableStateFlow("")
        val pregnancyCheck = _pregnancyCheck.asStateFlow()

        private val _pregnancyCheckByKorean = MutableStateFlow("")
        val pregnancyCheckByKorean = _pregnancyCheckByKorean.asStateFlow()

        private val _typeList = MutableStateFlow("")
        val typeList = _typeList.asStateFlow()

        private val _otherList = MutableStateFlow("")
        val otherList = _otherList.asStateFlow()

        private val _worseList = MutableStateFlow("")
        val worseList = _worseList.asStateFlow()

        private val _otherListByKorean = MutableStateFlow("")
        val otherListByKorean = _otherListByKorean.asStateFlow()

        private val _worseListByKorean = MutableStateFlow("")
        val worseListByKorean = _worseListByKorean.asStateFlow()

        private val _symptomByKorean = MutableStateFlow("")
        val symptomByKorean = _symptomByKorean.asStateFlow()

        private val _typeListByKorean = MutableStateFlow("")
        val typeListByKorean = _typeListByKorean.asStateFlow()

        private val _userLanguage = MutableStateFlow("")
        val userLanguage = _userLanguage.asStateFlow()

        private val _womenResponse = MutableStateFlow(WomenQuestionResponse())
        val womenResponse = _womenResponse.asStateFlow()

        private val _symptomContent = MutableStateFlow("")
        val symptomContent =
            _symptomContent
                .map {
                    ResourceUtil.getForeignString(context, _userLanguage.value, it)
                }.stateIn(
                    scope = viewModelScope,
                    initialValue = "",
                    started = SharingStarted.Lazily,
                )

        val symptomContentByKorean =
            _symptomContent
                .map {
                    ResourceUtil.getKoreanString(context, it)
                }.stateIn(
                    scope = viewModelScope,
                    initialValue = "",
                    started = SharingStarted.Lazily,
                )

        private val timeStamp: String =
            savedStateHandle.get<String>("timeStamp")
                ?: throw IllegalArgumentException("time stamp가 필요합니다!")

        private val uid: String =
            savedStateHandle.get<String>("uid")
                ?: throw IllegalArgumentException("uid가 필요합니다!")

        //   유저 건강 정보
        private val _userHealthInfo = MutableStateFlow(HealthInfo())
        val userHealthInfo = _userHealthInfo.asStateFlow()

        init {
            getUserHealthInfo()
            getUserLanguage()
            getSymptom()
        }

        // 로컬에 저장된 내용 불러오기
        private fun getUserHealthInfo() {
            viewModelScope.launch {
                context.userHealthPreferencesStore.data.collectLatest {
                    _userHealthInfo.value =
                        _userHealthInfo.value
                            .copy(
                                diseaseList = it.diseaseInfoList,
                                familyDiseaseList = it.familyDiseaseList,
                                allergyList = it.allergyInfoList,
                                drugList = it.drugInfoList,
                                drugTakingDuration = it.duration,
                                drugTakingCount = it.count,
                            ).apply {
                                Timber.d("건강 데이터 :$this")
                            }
                }
            }
        }

        private fun getUserLanguage() {
            viewModelScope.launch {
                context.userPreferencesStore.data.collectLatest {
                    _userLanguage.value = it.language
                }
            }
        }

        fun getSymptom() {
            val db = Firebase.firestore
            db
                .collection("symptom_result_$uid")
                .document(timeStamp)
                .get()
                .addOnSuccessListener {
                    val result =
                        it.toObject(WomenQuestionResponse::class.java)
                    if (result != null) {
                        _womenResponse.value = result

                        _symptomTitle.value = _womenResponse.value.symptomTitle
                        _symptomContent.value = _womenResponse.value.symptomContent

                        val typeList = mutableListOf<String>()
                        val otherList = mutableListOf<String>()
                        val worseList = mutableListOf<String>()

                        val typeListByKorean = mutableListOf<String>()
                        val otherListByKorean = mutableListOf<String>()
                        val worseListByKorean = mutableListOf<String>()

                        _womenResponse.value.type.forEach {
                            typeList.add(ResourceUtil.getForeignString(context, _userLanguage.value, it))
                            typeListByKorean.add(ResourceUtil.getKoreanString(context, it))
                        }

                        _womenResponse.value.otherSymptom.forEach {
                            otherList.add(ResourceUtil.getForeignString(context, _userLanguage.value, it))
                            otherListByKorean.add(ResourceUtil.getKoreanString(context, it))
                        }

                        _womenResponse.value.worstList.forEach {
                            worseList.add(ResourceUtil.getForeignString(context, _userLanguage.value, it))
                            worseListByKorean.add(ResourceUtil.getKoreanString(context, it))
                        }

                        _pregnancyCheck.value =
                            ResourceUtil.getForeignString(context, _userLanguage.value, _womenResponse.value.availablePregnancy)
                        _pregnancyCheckByKorean.value = ResourceUtil.getKoreanString(context, _womenResponse.value.availablePregnancy)

                        if (typeList.isEmpty()) {
                            _typeList.value = "해당 없음"
                        } else {
                            _typeList.value = typeList.joinToString(", ")
                        }

                        _worseList.value = if (worseList.isEmpty()) "해당 없음" else worseList.joinToString(", ")
                        _otherList.value = if (otherList.isEmpty())"해당 없음" else otherList.joinToString(", ")

                        _typeListByKorean.value = if (typeListByKorean.isEmpty()) "해당 없음" else typeListByKorean.joinToString(", ")
                        _otherListByKorean.value = if (otherListByKorean.isEmpty()) "해당 없음" else otherListByKorean.joinToString(", ")
                        _worseListByKorean.value = if (worseListByKorean.isEmpty()) "해당 없음" else worseListByKorean.joinToString(", ")
                    }
                }.addOnFailureListener {
                    it.printStackTrace()
                    Timber.d("failed to get dental data :${it.message}")
                }
        }
    }
