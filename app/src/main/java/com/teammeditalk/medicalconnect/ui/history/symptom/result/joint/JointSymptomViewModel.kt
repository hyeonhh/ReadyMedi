package com.teammeditalk.medicalconnect.ui.history.symptom.result.joint

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.teammeditalk.medicalconnect.data.model.question.JointQuestionResponse
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
class JointSymptomViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        @ApplicationContext val context: Context,
    ) : ViewModel() {
        private val _jointResponse = MutableStateFlow(JointQuestionResponse())
        val jointResponse = _jointResponse.asStateFlow()

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

        private fun getUserLanguage() {
            viewModelScope.launch {
                context.userPreferencesStore.data.collectLatest {
                    _userLanguage.value = it.language
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
