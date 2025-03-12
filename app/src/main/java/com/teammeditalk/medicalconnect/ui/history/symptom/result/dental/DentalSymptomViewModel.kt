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
        private val savedStateHandle: SavedStateHandle,
        @ApplicationContext val context: Context,
    ) : ViewModel() {
        init {
            getUserHealthInfo()
        }

        //   유저 건강 정보
        private val _userHealthInfo = MutableStateFlow(HealthInfo())
        val userHealthInfo = _userHealthInfo.asStateFlow()

        private val _dentalResponse = MutableStateFlow(DentalQuestionResponse())
        val dentalResponse = _dentalResponse.asStateFlow()

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

        fun getSymptom(
            uid: String,
            timeStamp: String,
        ) {
            val db = Firebase.firestore
            Timber.d("uid :$uid")
            Timber.d("timeStamp :$timeStamp")
            db
                .collection("symptom_result_$uid")
                .document(timeStamp)
                .get()
                .addOnSuccessListener {
                    val result =
                        it.toObject(DentalQuestionResponse::class.java)
                    Timber.d("데이터 리셜드 :$result")
                    if (result != null) {
                        _dentalResponse.value = result
                        _dentalResponse.value =
                            _dentalResponse.value.copy(
                                anesHistory = result.anesHistory,
                            )
                        Timber.d("데이터 :${_dentalResponse.value}")
                    }
                    Timber.d("success to get dental data :$it")
                }.addOnFailureListener {
                    it.printStackTrace()
                    Timber.d("failed to get dental data :${it.message}")
                }
        }
    }
