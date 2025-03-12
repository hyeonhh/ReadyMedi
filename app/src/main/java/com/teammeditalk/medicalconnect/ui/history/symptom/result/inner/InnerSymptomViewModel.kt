package com.teammeditalk.medicalconnect.ui.history.symptom.result.inner

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.teammeditalk.medicalconnect.data.model.info.HealthInfo
import com.teammeditalk.medicalconnect.data.model.question.InnerQuestionResponse
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
class InnerSymptomViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        @ApplicationContext val context: Context,
    ) : ViewModel() {
        private val _innerResponse = MutableStateFlow(InnerQuestionResponse())
        val innerResponse = _innerResponse.asStateFlow()

        //   유저 건강 정보
        private val _userHealthInfo = MutableStateFlow(HealthInfo())
        val userHealthInfo = _userHealthInfo.asStateFlow()

        // Safe Args로 전달된 ID를 바로 가져옵니다
        private val timeStamp: String =
            savedStateHandle.get<String>("timeStamp")
                ?: throw IllegalArgumentException("time stamp가 필요합니다!")

        private val uid: String =
            savedStateHandle.get<String>("uid")
                ?: throw IllegalArgumentException("uid가 필요합니다!")

        init {
            getUserHealthInfo()
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

        fun getSymptom() {
            val db = Firebase.firestore
            db
                .collection("symptom_result_$uid")
                .document(timeStamp)
                .get()
                .addOnSuccessListener {
                    val result =
                        it.toObject(InnerQuestionResponse::class.java)
                    if (result != null) {
                        _innerResponse.value = result
                    }
                }.addOnFailureListener {
                    it.printStackTrace()
                    Timber.d("failed to get dental data :${it.message}")
                }
        }
    }
