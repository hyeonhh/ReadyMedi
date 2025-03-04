package com.teammeditalk.medicalconnect.ui.question

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.teammeditalk.medicalconnect.data.model.info.HealthInfo
import com.teammeditalk.medicalconnect.data.model.question.DentalQuestionResponse
import com.teammeditalk.medicalconnect.data.model.question.GeneralQuestionResponse
import com.teammeditalk.medicalconnect.data.model.question.InnerQuestionResponse
import com.teammeditalk.medicalconnect.data.model.question.JointQuestionResponse
import com.teammeditalk.medicalconnect.data.model.question.WomenQuestionResponse
import com.teammeditalk.medicalconnect.data.model.symptom.SymptomResponse
import com.teammeditalk.medicalconnect.data.serializer.UserAuthPreferencesSerializer.userAuthPreferencesStore
import com.teammeditalk.medicalconnect.data.serializer.UserHealthPreferencesSerializer.userHealthPreferencesStore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) : ViewModel() {
        init {
            getUserAuthInfo()
            getUserHealthInfo()
        }

        private val db = Firebase.firestore

        private val _selectedRegularity = MutableStateFlow("")
        val selectedRegularity = _selectedRegularity.asStateFlow()

        private val _selectedIsAvailablePregnancy = MutableStateFlow("")
        val selectedIsAvailablePregnancy = _selectedIsAvailablePregnancy.asStateFlow()

        private val _selectedWomenLastDate = MutableStateFlow("")
        val selectedWomenLastDate = _selectedWomenLastDate.asStateFlow()

        private val _selectedRegion = MutableStateFlow("")
        val selectedRegion = _selectedRegion.asStateFlow()

        private val _injuryHistory = MutableStateFlow("")
        val injuryHistory = _injuryHistory.asStateFlow()

        // 치과 결과
        private val _dentalResult = MutableStateFlow(DentalQuestionResponse())
        val dentalResult = _dentalResult.asStateFlow()

        // 정형 결과
        private val _jointResult = MutableStateFlow(JointQuestionResponse())
        val jointResult = _jointResult.asStateFlow()

        // 내과 결과
        private val _innerResult = MutableStateFlow(InnerQuestionResponse())
        val innerResult = _innerResult.asStateFlow()

        // 일반 결과
        private val _generalResult = MutableStateFlow(GeneralQuestionResponse())
        val generalResult = _generalResult.asStateFlow()

        // 산부 결과
        private val _womenResult = MutableStateFlow(WomenQuestionResponse())
        val womenResult = _womenResult.asStateFlow()

        // 유저 로그인 정보
        private val _userId = MutableStateFlow("")
        val userId = _userId.asStateFlow()

        private val _sideEffect = MutableStateFlow("")
        val sideEffect = _sideEffect.asStateFlow()

        private val _anesthesiaHistory = MutableStateFlow(false)
        val anesthesiaHistory = _anesthesiaHistory.asStateFlow()

        //   유저 건강 정보
        private val _userHealthInfo = MutableStateFlow(HealthInfo())
        val userHealthInfo = _userHealthInfo.asStateFlow()

        // 번역 내용
        private val _translatedResult = MutableStateFlow(SymptomResponse())
        val translatedResult = _translatedResult.asStateFlow()

        // 최종 변수
        private val _questionResult = MutableStateFlow(SymptomResponse())
        val questionResult = _questionResult.asStateFlow()

        private val _additionalInput = MutableStateFlow("")
        val additionalInput = _additionalInput.asStateFlow()

        private val _selectedReleaseList = MutableStateFlow(emptyList<String>())
        val selectedReleaseList = _selectedReleaseList.asStateFlow()

        private val _selectedWorseList = MutableStateFlow(emptyList<String>())
        val selectedWorseList = _selectedWorseList.asStateFlow()

        private val _selectedOtherList = MutableStateFlow(emptyList<String>())
        val selectedOtherList = _selectedOtherList.asStateFlow()

        private val _selectedType = MutableStateFlow(emptyList<String>())
        val selectedType = _selectedType.asStateFlow()

        private val _selectedDegree = MutableStateFlow(0.0f)
        val selectedDegree = _selectedDegree.asStateFlow()

        private val _selectedSymptom = MutableStateFlow("" to "")
        val selectedSymptom = _selectedSymptom.asStateFlow()

        private val _category = MutableStateFlow("")
        val category = _category.asStateFlow()

        private val _selectedDate = MutableStateFlow("")
        val selectedDate = _selectedDate.asStateFlow()

        private val _selectedDuration = MutableStateFlow("")
        val selectedDuration = _selectedDuration.asStateFlow()

        fun setLastPeriod(content: String) {
            _selectedWomenLastDate.value = content
        }

        fun setRegularity(content: String) {
            _selectedRegularity.value = content
        }

        fun setPregnancyAvailability(content: String) {
            _selectedIsAvailablePregnancy.value = content
        }

        fun setJointInjuryHistory(content: String) {
            _injuryHistory.value = content
        }

        fun setDentalSideEffect(content: String) {
            _sideEffect.value = content
        }

        // todo : 파베에 치과 데이터 저장하기
        fun saveDentalResponse() {
            val timeStamp = System.currentTimeMillis()
            val sdf = SimpleDateFormat("yyyy-MM-dd kk:mm:ss")
            val toDate = sdf.format(timeStamp)

            _dentalResult.value =
                DentalQuestionResponse(
                    timeStamp = toDate,
                    hospitalType = _category.value,
                    symptomTitle = _selectedSymptom.value.first,
                    symptomContent = _selectedSymptom.value.second,
                    startDate = _selectedDate.value,
                    type = _selectedType.value.toString(),
                    degree = _selectedDegree.value.toInt().toString(),
                    duration = _selectedDuration.value,
                    worseList = _selectedWorseList.value,
                    otherSymptom = _selectedOtherList.value,
                    additionalInput = _additionalInput.value,
                    anesHistory = _anesthesiaHistory.value,
                    sideEffect = _sideEffect.value,
                )

            if (_userId.value != "") {
                db
                    .collection("symptom_result_${_userId.value}")
                    .document(toDate.toString())
                    .set(_dentalResult.value)
                    .addOnSuccessListener {
                        Timber.d("success to save dental response")
                    }.addOnFailureListener {
                        Timber.d("failed  to save dental response, ${it.message}")
                    }
            }
        }

        // todo : 파베에 정형외과 데이터 저장
        fun saveJointResponse() {
            val timeStamp = System.currentTimeMillis()
            val sdf = SimpleDateFormat("yyyy-MM-dd kk:mm:ss")
            val toDate = sdf.format(timeStamp)

            _jointResult.value =
                JointQuestionResponse(
                    timeStamp = toDate,
                    region = _selectedRegion.value,
                    hospitalType = _category.value,
                    symptomTitle = _selectedSymptom.value.first,
                    symptomContent = _selectedSymptom.value.second,
                    startDate = _selectedDate.value,
                    type = _selectedType.value.toString(),
                    degree = _selectedDegree.value.toInt().toString(),
                    duration = _selectedDuration.value,
                    worseList = _selectedWorseList.value,
                    otherSymptom = _selectedOtherList.value,
                    additionalInput = _additionalInput.value,
                    injuryHistory = _injuryHistory.value,
                )

            if (_userId.value != "") {
                db
                    .collection("symptom_result_${_userId.value}")
                    .document(toDate.toString())
                    .set(_jointResult.value)
                    .addOnSuccessListener {
                        Timber.d("success to save dental response")
                    }.addOnFailureListener {
                        Timber.d("failed  to save dental response, ${it.message}")
                    }
            }
        }

        // todo : 파베에 일반외과 데이터 저장
        fun saveGeneralResponse() {
            val timeStamp = System.currentTimeMillis()
            val sdf = SimpleDateFormat("yyyy-MM-dd kk:mm:ss")
            val toDate = sdf.format(timeStamp)

            _generalResult.value =
                GeneralQuestionResponse(
                    timeStamp = toDate,
                    region = _selectedRegion.value,
                    hospitalType = _category.value,
                    symptomTitle = _selectedSymptom.value.first,
                    symptomContent = _selectedSymptom.value.second,
                    startDate = _selectedDate.value,
                    type = _selectedType.value.toString(),
                    degree = _selectedDegree.value.toInt().toString(),
                    duration = _selectedDuration.value,
                    worseList = _selectedWorseList.value,
                    otherSymptom = _selectedOtherList.value,
                    additionalInput = _additionalInput.value,
                )

            if (_userId.value != "") {
                db
                    .collection("symptom_result_${_userId.value}")
                    .document(toDate.toString())
                    .set(_generalResult.value)
                    .addOnSuccessListener {
                        Timber.d("success to save dental response")
                    }.addOnFailureListener {
                        Timber.d("failed  to save dental response, ${it.message}")
                    }
            }
        }

        // todo : 파베에 산부인과 데이터 저장
        fun saveWomenResponse() {
            val timeStamp = System.currentTimeMillis()
            val sdf = SimpleDateFormat("yyyy-MM-dd kk:mm:ss")
            val toDate = sdf.format(timeStamp)

            _womenResult.value =
                WomenQuestionResponse(
                    timeStamp = toDate,
                    hospitalType = _category.value,
                    symptomTitle = _selectedSymptom.value.first,
                    symptomContent = _selectedSymptom.value.second,
                    startDate = _selectedDate.value,
                    type = _selectedType.value.toString(),
                    degree = _selectedDegree.value.toInt().toString(),
                    duration = _selectedDuration.value,
                    otherSymptom = _selectedOtherList.value,
                    additionalInput = _additionalInput.value,
                    lastDate = _selectedWomenLastDate.value,
                    isAvailablePregnancy = _selectedIsAvailablePregnancy.value,
                    regularity = _selectedRegularity.value,
                )

            if (_userId.value != "") {
                db
                    .collection("symptom_result_${_userId.value}")
                    .document(toDate.toString())
                    .set(_womenResult.value)
                    .addOnSuccessListener {
                        Timber.d("success to save dental response")
                    }.addOnFailureListener {
                        Timber.d("failed  to save dental response, ${it.message}")
                    }
            }
        }

// todo : 파베에 내과 저장
        fun saveInnerResponse() {
            val timeStamp = System.currentTimeMillis()
            val sdf = SimpleDateFormat("yyyy-MM-dd kk:mm:ss")
            val toDate = sdf.format(timeStamp)

            _innerResult.value =
                InnerQuestionResponse(
                    timeStamp = toDate,
                    region = _selectedRegion.value,
                    hospitalType = _category.value,
                    symptomTitle = _selectedSymptom.value.first,
                    symptomContent = _selectedSymptom.value.second,
                    startDate = _selectedDate.value,
                    type = _selectedType.value.toString(),
                    degree = _selectedDegree.value.toInt().toString(),
                    duration = _selectedDuration.value,
                    worseList = _selectedWorseList.value,
                    otherSymptom = _selectedOtherList.value,
                    additionalInput = _additionalInput.value,
                )

            if (_userId.value != "") {
                db
                    .collection("symptom_result_${_userId.value}")
                    .document(toDate.toString())
                    .set(_innerResult.value)
                    .addOnSuccessListener {
                        Timber.d("success to save dental response")
                    }.addOnFailureListener {
                        Timber.d("failed  to save dental response, ${it.message}")
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

        private fun getUserAuthInfo() {
            viewModelScope.launch {
                context.userAuthPreferencesStore.data.collectLatest {
                    _userId.value = it.uid
                }
            }
        }

        fun setOtherSymptomList(other: List<String>) {
            _selectedOtherList.value = other
            Timber.d("동반 :${_selectedOtherList.value}")
        }

        fun setAnesthesiaHistory(answer: Boolean) {
            _anesthesiaHistory.value = answer
            Timber.d("마취 :${_anesthesiaHistory.value}")
        }

        // 증상 성격
        fun setSymptomType(type: List<String>) {
            _selectedType.value = type
            Timber.d("type :${_selectedType.value}")
        }

        // 추가 전달
        fun setAdditionalInput(input: String) {
            _additionalInput.value = input
            Timber.d("입력 :${_additionalInput.value}")
        }

        // 증상 완화 요인
        fun selectReleaseList(releaseList: List<String>) {
            _selectedReleaseList.value = releaseList
            Timber.d("완화 :${_selectedReleaseList.value}")
        }

        // 증상 악화 요인
        fun selectWorseList(worseList: List<String>) {
            _selectedWorseList.value = worseList
            Timber.d("악화 :${_selectedWorseList.value}")
        }

        // 통증 정도 저장
        fun selectDegree(degree: Float) {
            _selectedDegree.value = degree
            Timber.d("통증 정도 :${_selectedDegree.value}")
        }

        // 증상 지속 시간 저장하기
        fun selectSymptomDuration(duration: String) {
            _selectedDuration.value = duration
            Timber.d("지속성 :${_selectedDuration.value}")
        }

        // 증상 발생 날짜 저장하기
        fun selectSymptomStartDate(selectedDate: String) {
            _selectedDate.value = selectedDate
            Timber.d("발생 시작 :${_selectedDate.value}")
        }

        // 선택한 증상과 진료과 카테고리
        fun selectSymptom(
            selectedCategory: String,
            selectedSymptom: String,
            hospitalCategory: String,
        ) {
            _selectedSymptom.value = selectedCategory to selectedSymptom
            _category.value = hospitalCategory

            Timber.d("저장된 증상 카테고리 :${_selectedSymptom.value.first}\n증상 내용 : ${_selectedSymptom.value.second}\n병원 타입 : ${_category.value}")
        }
    }
