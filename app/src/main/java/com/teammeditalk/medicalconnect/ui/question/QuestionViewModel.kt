package com.teammeditalk.medicalconnect.ui.question

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import com.teammeditalk.medicalconnect.data.model.info.HealthInfo
import com.teammeditalk.medicalconnect.data.model.question.DentalQuestionResponse
import com.teammeditalk.medicalconnect.data.model.question.GeneralQuestionResponse
import com.teammeditalk.medicalconnect.data.model.question.InnerQuestionResponse
import com.teammeditalk.medicalconnect.data.model.question.JointQuestionResponse
import com.teammeditalk.medicalconnect.data.model.question.WomenQuestionResponse
import com.teammeditalk.medicalconnect.data.model.symptom.SymptomResponse
import com.teammeditalk.medicalconnect.data.serializer.UserAuthPreferencesSerializer.userAuthPreferencesStore
import com.teammeditalk.medicalconnect.data.serializer.UserHealthPreferencesSerializer.userHealthPreferencesStore
import com.teammeditalk.medicalconnect.data.serializer.UserPreferencesSerializer.userPreferencesStore
import com.teammeditalk.medicalconnect.ui.event.SingleLiveEvent
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
            getUserLanguageInfo()
        }

        fun setSymptomContentId(contentId: String) {
            _symptomContentId.value = contentId
        }

        private val _pregnancyCheck = MutableStateFlow("")
        val pregnancyCheck = _pregnancyCheck.asStateFlow()
        private val _pregnancyCheckByKorean = MutableStateFlow("")
        val pregnancyCheckByKorean = _pregnancyCheckByKorean.asStateFlow()

        private val _regionId = MutableStateFlow("")
        val regionId = _regionId.asStateFlow()

        private val _sideEffectId = MutableStateFlow("")
        val sideEffectId = _sideEffectId.asStateFlow()

        private val _symptomTitleId = MutableStateFlow("")
        val symptomTitleId = _symptomTitleId.asStateFlow()

        private val _otherSymptomIdList = MutableStateFlow(emptyList<String>())
        val otherSymptomIdList = _otherSymptomIdList.asStateFlow()

        private val _worseIdList = MutableStateFlow(emptyList<String>())
        val worseIdList = _worseIdList.asStateFlow()

        private val _symptomContentId = MutableStateFlow("")
        val symptomContentId = _symptomContentId.asStateFlow()

        private val _typeId = MutableStateFlow(emptyList<String>())
        val typeId = _typeId.asStateFlow()

        private val _userLanguage = MutableStateFlow("")
        val userLanguage = _userLanguage.asStateFlow()

        fun setOtherSymptomId(list: List<String>) {
            _otherSymptomIdList.value = list
            Timber.d("othersymptom id :${_otherSymptomIdList.value}")
        }

        fun setWorseListId(list: List<String>) {
            _worseIdList.value = list
            Timber.d("worse idlist :${_worseIdList.value}")
        }

        fun setTypeId(type: List<String>) {
            Timber.d("type idlist :${_worseIdList.value}")
            _typeId.value = type
        }

        //  번역 이벤트
        private val _translateEvent = SingleLiveEvent<Any>()
        val translateEvent: LiveData<Any> get() = _translateEvent

        private val _translateFailure = SingleLiveEvent<Any>()
        val translateFailure: LiveData<Any> get() = _translateFailure

        private fun processTranslateFail() {
            _translateFailure.call()
        }

        private fun navigateScreen() {
            _translateEvent.call()
        }

        var womenRegularity = ""
        var pregnancyAvailableByKorean = ""
        var innerWorstListByKorean = ""
        var innerPainTypeByKorean = ""

        // todo : 한국어 버전 저장하기

        var otherListByKorean = ""

        var koreanSymptom = ""

        var regionByKorean = ""

        fun setWomenMenstruationRegularity(content: String) {
            womenRegularity = content
        }

        fun setOtherListByKorean(list: List<String>) {
            otherListByKorean = if (list.isEmpty()) "해당 없음" else list.joinToString(", ")
        }

        fun setInnerWorstListByKorean(list: List<String>) {
            innerWorstListByKorean = if (list.isEmpty()) "해당 없음" else list.joinToString(", ")
        }

        fun setPainTypeByKorean(type: List<String>) {
            innerPainTypeByKorean =
                if (type.isEmpty()) {
                    "해당 없음"
                } else {
                    type.joinToString(", ")
                }
        }

        fun setSymptomRegionByKorean(region: String) {
            regionByKorean = region
        }

        fun setSymptomByKorean(symptom: String) {
            koreanSymptom = symptom
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

        private val _injuryHistoryTranslated = MutableStateFlow("")
        val injuryHistoryTranslated = _injuryHistoryTranslated.asStateFlow()

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
        val anesthesiaHistory =
            _anesthesiaHistory
                .map {
                    if (it) {
                        "예"
                    } else {
                        "아니요"
                    }
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.Eagerly,
                    initialValue = "아니요",
                )

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

        private val _additionalInputTranslated = MutableStateFlow("")
        val additionalInputTranslated = _additionalInputTranslated.asStateFlow()

        private val _selectedReleaseList = MutableStateFlow(emptyList<String>())
        val selectedReleaseList = _selectedReleaseList.asStateFlow()

        private val _selectedWorseList = MutableStateFlow(emptyList<String>())
        val selectedWorseList =
            _selectedWorseList
                .map {
                    if (it.isEmpty()) "해당 없음" else it.joinToString(",")
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.Eagerly,
                    initialValue = "해당 없음",
                )

        private val _selectedOtherList = MutableStateFlow(emptyList<String>())
        val selectedOtherList =
            _selectedOtherList
                .map {
                    if (it.isEmpty()) "해당 없음" else it.joinToString(",")
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.Eagerly,
                    initialValue = "해당 없음",
                )

        private val _selectedType = MutableStateFlow(emptyList<String>())
        val selectedType =
            _selectedType
                .map {
                    if (it.isEmpty()) "해당 없음" else it.joinToString(",")
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.Eagerly,
                    initialValue = "해당 없음",
                )

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
            _pregnancyCheck.value = ResourceUtil.getForeignString(context, _userLanguage.value, content)
            _pregnancyCheckByKorean.value = ResourceUtil.getKoreanString(context, content)
        }

        fun setJointInjuryHistory(content: String) {
            _injuryHistory.value = content
            translateJointInjury()
        }

        fun setDentalSideEffect(content: String) {
            _sideEffect.value = content
        }

        private fun translateEnglishToKorean(lang: String) {
            viewModelScope.launch {
                val options =
                    TranslatorOptions
                        .Builder()
                        .setSourceLanguage(
                            when (lang) {
                                "en" -> TranslateLanguage.ENGLISH
                                "ja" -> TranslateLanguage.JAPANESE
                                "zh" -> TranslateLanguage.CHINESE
                                else -> TranslateLanguage.ENGLISH
                            },
                        ).setTargetLanguage(TranslateLanguage.KOREAN)
                        .build()
                val translator = Translation.getClient(options)

                val conditions =
                    DownloadConditions
                        .Builder()
                        .requireWifi()
                        .build()
                translator
                    .downloadModelIfNeeded(conditions)
                    .addOnSuccessListener {
                        translator
                            .translate(_additionalInput.value)
                            .addOnSuccessListener {
                                _additionalInputTranslated.value = it
                                navigateScreen()
                            }.addOnFailureListener {
                                Timber.d("Failed to translate :${it.message}")
                                processTranslateFail()
                            }
                    }.addOnFailureListener { exception ->
                        exception.printStackTrace()
                        Timber.d("Failed to download :${exception.message}")
                        processTranslateFail()
                    }
            }
        }

        private fun translateJointInjury() {
            viewModelScope.launch {
                val options =
                    TranslatorOptions
                        .Builder()
                        .setSourceLanguage(TranslateLanguage.KOREAN)
                        .setTargetLanguage(TranslateLanguage.ENGLISH)
                        .build()
                val koreanEnglishTranslator = Translation.getClient(options)

                koreanEnglishTranslator
                    .translate(_injuryHistory.value)
                    .addOnSuccessListener {
                        _injuryHistoryTranslated.value = it
                    }.addOnFailureListener { exception ->
                        exception.printStackTrace()
                        Timber.d("Failed to translate :${exception.message}")
                    }
            }
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
                    symptomContent = _symptomContentId.value,
                    startDate = _selectedDate.value,
                    type = _typeId.value,
                    degree = _selectedDegree.value.toString(),
                    duration = _selectedDuration.value,
                    worseList = _worseIdList.value,
                    otherSymptom = otherSymptomIdList.value,
                    additionalInput = _additionalInput.value,
                    additionalInputByKorean = _additionalInputTranslated.value,
                    anesHistory = if (_anesthesiaHistory.value) "예" else "아니요",
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
                    symptomContent = _symptomContentId.value,
                    startDate = _selectedDate.value,
                    type = _typeId.value,
                    degree = _selectedDegree.value.toString(),
                    duration = _selectedDuration.value,
                    worseList = _worseIdList.value,
                    otherSymptom = _otherSymptomIdList.value,
                    additionalInput = _additionalInput.value,
                    additionalInputByKorean = _additionalInputTranslated.value,
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

        fun setRegion(content: String) {
            _selectedRegion.value = content
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
                    symptomContent = _symptomContentId.value,
                    startDate = _selectedDate.value,
                    type = _typeId.value,
                    degree = _selectedDegree.value.toString(),
                    duration = _selectedDuration.value,
                    worseList = _worseIdList.value,
                    otherSymptom = _otherSymptomIdList.value,
                    additionalInput = _additionalInput.value,
                    additionalInputByKorean = _additionalInputTranslated.value,
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
                    symptomContent = _symptomContentId.value,
                    startDate = _selectedDate.value,
                    type = _typeId.value,
                    degree = _selectedDegree.value.toString(),
                    duration = _selectedDuration.value,
                    otherSymptom = _otherSymptomIdList.value,
                    additionalInput = _additionalInput.value,
                    additionalInputByKorean = _additionalInputTranslated.value,
                    lastDate = _selectedWomenLastDate.value,
                    availablePregnancy = _selectedIsAvailablePregnancy.value,
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
                    symptomContent = _symptomContentId.value,
                    startDate = _selectedDate.value,
                    type = _typeId.value,
                    degree = _selectedDegree.value.toString(),
                    duration = _selectedDuration.value,
                    worseList = _worseIdList.value,
                    otherSymptom = _otherSymptomIdList.value,
                    additionalInput = _additionalInput.value,
                    additionalInputByKorean = _additionalInputTranslated.value,
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
                    Timber.d("건강 데이터 :$it")
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

        // 사용자 언어 불러오기
        private fun getUserLanguageInfo() {
            viewModelScope.launch {
                context.userPreferencesStore.data.collectLatest {
                    Timber.d("언어 :$it")
                    _userLanguage.value = it.language
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
            translateEnglishToKorean(_userLanguage.value)
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
