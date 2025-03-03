package com.teammeditalk.medicalconnect.ui.question

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teammeditalk.medicalconnect.data.model.info.HealthInfo
import com.teammeditalk.medicalconnect.data.model.symptom.SymptomResponse
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
class QuestionViewModel
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) : ViewModel() {
        init {
            getUserHealthInfo()
        }

        private val _anesthesiaHistory = MutableStateFlow(false)
        val anesthesiaHistory = _anesthesiaHistory.value

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

        fun setOtherSymptomList(other: List<String>) {
            _selectedOtherList.value = other
        }

        fun setAnesthesiaHistory(answer: Boolean) {
            _anesthesiaHistory.value = answer
        }

        // 증상 성격
        fun setSymptomType(type: List<String>) {
            _selectedType.value = type
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
            Timber.d("발생 시작 :${_selectedDegree.value}")
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
