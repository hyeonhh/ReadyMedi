package com.teammeditalk.medicalconnect.ui.history.symptom.result.dental

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.teammeditalk.medicalconnect.data.model.question.DentalQuestionResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DentalSymptomViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        @ApplicationContext val context: Context,
    ) : ViewModel() {
        private val _dentalResponse = MutableStateFlow(DentalQuestionResponse())
        val dentalResponse = _dentalResponse.asStateFlow()

        // Safe Args로 전달된 ID를 바로 가져옵니다
        private val timeStamp: String =
            savedStateHandle.get<String>("timeStamp")
                ?: throw IllegalArgumentException("time stamp가 필요합니다!")

        private val uid: String =
            savedStateHandle.get<String>("uid")
                ?: throw IllegalArgumentException("uid가 요합니다!")

        init {
            getSymptom()
        }

        fun getSymptom() {
            Timber.d("timestamp:$timeStamp")
            val db = Firebase.firestore
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
                        Timber.d("데이터 :${_dentalResponse.value}")
                    }
                    Timber.d("success to get dental data :$it")
                }.addOnFailureListener {
                    it.printStackTrace()
                    Timber.d("failed to get dental data :${it.message}")
                }
        }
    }
