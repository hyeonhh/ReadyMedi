package com.teammeditalk.medicalconnect.ui.history.symptom

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.firestore
import com.teammeditalk.medicalconnect.data.model.history.SymptomHistory
import com.teammeditalk.medicalconnect.data.model.question.DentalQuestionResponse
import com.teammeditalk.medicalconnect.data.model.question.GeneralQuestionResponse
import com.teammeditalk.medicalconnect.data.model.question.InnerQuestionResponse
import com.teammeditalk.medicalconnect.data.model.question.JointQuestionResponse
import com.teammeditalk.medicalconnect.data.model.question.WomenQuestionResponse
import com.teammeditalk.medicalconnect.data.serializer.UserAuthPreferencesSerializer.userAuthPreferencesStore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MySymptomHistoryViewModel
    @Inject
    constructor(
        @ApplicationContext val context: Context,
    ) : ViewModel() {
        private val _history = MutableStateFlow(listOf<SymptomHistory>())
        val history =
            _history
                .map {
                    it.sortedBy { it.timeStamp }
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.Eagerly,
                    initialValue = emptyList(),
                )

        private val _userId = MutableStateFlow("")
        val userId = _userId.asStateFlow()

        init {
            getUserAuthInfo()
        }

        private fun getUserAuthInfo() {
            viewModelScope.launch {
                context.userAuthPreferencesStore.data.collectLatest {
                    _userId.value = it.uid
                    Timber.d("_userId :${_userId.value}")
                    if (_userId.value != "") getHistoryList()
                }
            }
        }

        suspend fun processData(documents: List<DocumentSnapshot>) {
            val list = mutableListOf<SymptomHistory>()
            viewModelScope
                .async {
                    for (document in documents) {
                        Timber.d("document :${document.data} \n")
                        // todo : 진료과별로 분류해야함
                        if (document.data.toString().contains("치과")) {
                            val result =
                                document.toObject(DentalQuestionResponse::class.java)
                            if (result != null) {
                                list.add(
                                    SymptomHistory(
                                        hospitalType = "치과",
                                        symptomContent = result.symptomContent,
                                        symptomTitle = result.symptomTitle,
                                        timeStamp = result.timeStamp,
                                    ),
                                )
                            }
                        } else if (document.data.toString().contains("일반")) {
                            val result =
                                document.toObject(GeneralQuestionResponse::class.java)
                            if (result != null) {
                                list.add(
                                    SymptomHistory(
                                        hospitalType = "일반",
                                        symptomContent = result.symptomContent,
                                        symptomTitle = result.symptomTitle,
                                        timeStamp = result.timeStamp,
                                    ),
                                )
                            }
                        } else if (document.data.toString().contains("정형")) {
                            val result =
                                document.toObject(JointQuestionResponse::class.java)
                            if (result != null) {
                                list.add(
                                    SymptomHistory(
                                        hospitalType = "정형",
                                        symptomContent = result.symptomContent,
                                        symptomTitle = result.symptomTitle,
                                        timeStamp = result.timeStamp,
                                    ),
                                )
                            }
                        } else if ((document.data.toString().contains("산부"))) {
                            val result =
                                document.toObject(WomenQuestionResponse::class.java)
                            if (result != null) {
                                list.add(
                                    SymptomHistory(
                                        hospitalType = "산부",
                                        symptomContent = result.symptomContent,
                                        symptomTitle = result.symptomTitle,
                                        timeStamp = result.timeStamp,
                                    ),
                                )
                            }
                        } else {
                            val result =
                                document.toObject(InnerQuestionResponse::class.java)
                            if (result != null) {
                                list.add(
                                    SymptomHistory(
                                        hospitalType = "내과",
                                        symptomContent = result.symptomContent,
                                        symptomTitle = result.symptomTitle,
                                        timeStamp = result.timeStamp,
                                    ),
                                )
                            }
                        }
                    }
                }.await()
            _history.value = list
            Timber.d("history :${_history.value}")
        }

        fun getHistoryList() {
            if (_userId.value != "") {
                val db = Firebase.firestore
                val collection = db.collection("symptom_result_${_userId.value}")
                collection
                    .get()
                    .addOnSuccessListener {
                        viewModelScope.launch {
                            processData(documents = it.documents)
                        }
                    }.addOnFailureListener {
                        it.printStackTrace()
                        Timber.d("failed to get document :${it.message}")
                    }
            }
        }
    }
