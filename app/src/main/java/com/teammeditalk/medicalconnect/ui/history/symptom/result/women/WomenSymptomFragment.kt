package com.teammeditalk.medicalconnect.ui.history.symptom.result.women

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.LayoutCommonQuestionResultBinding
import com.teammeditalk.medicalconnect.databinding.LayoutHospitalTypeBinding
import com.teammeditalk.medicalconnect.databinding.LayoutHospitalVersionWomenBinding
import com.teammeditalk.medicalconnect.databinding.LayoutWomenCurrentSymptomBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class WomenSymptomFragment :
    BaseFragment<LayoutCommonQuestionResultBinding>(
        LayoutCommonQuestionResultBinding::inflate,
    ) {
    private val viewModel: WomenSymptomViewModel by viewModels()
    private val navController by lazy { findNavController() }
    private lateinit var inflater: LayoutInflater

    private fun setCurrentSymptomBinding() {
        // 사용자용 현재 증상 레이아웃 연결하기
        val currentSymptomFrame = binding.layoutFrame
        val womenCurrentSymptomBinding = LayoutWomenCurrentSymptomBinding.inflate(inflater, currentSymptomFrame, false)

        womenCurrentSymptomBinding.useWomenVM = true
        womenCurrentSymptomBinding.womenVM = viewModel
        womenCurrentSymptomBinding.lifecycleOwner = viewLifecycleOwner

        currentSymptomFrame.addView(womenCurrentSymptomBinding.root)
    }

    private fun onSwitchClick() {
        binding.btnSwitch.setOnCheckedChangeListener { _, isChecked ->
            binding.ivTooltip.visibility = View.INVISIBLE
            if (isChecked) {
                binding.layoutHospitalVersion.visibility = View.VISIBLE
                binding.layoutUser.visibility = View.GONE
            } else {
                binding.layoutHospitalVersion.visibility = View.GONE
                binding.layoutUser.visibility = View.VISIBLE
            }
        }
    }

    private fun setCurrentSymptomToHospital() {
        // 의료진용 보고서
        val hospitalContentContainer = binding.layoutHospitalVersion
        val hospitalReportBinding = LayoutHospitalVersionWomenBinding.inflate(inflater, hospitalContentContainer, false)

        // todo : additional_input & 공툥 요소 데이터 바인딩 처리하기
        hospitalReportBinding.currentSymptom.useWomenVM = true
        hospitalReportBinding.currentSymptom.womenVM = viewModel
        hospitalReportBinding.currentSymptom.lifecycleOwner = viewLifecycleOwner

        lifecycleScope.launch {
            viewModel.userHealthInfo.collectLatest {
                hospitalReportBinding.familyDiseaseAndDrug.tvFamilyDisease.text =
                    if (it.familyDiseaseList.isEmpty()) "해당 없음" else it.familyDiseaseList.joinToString(", ")
                hospitalReportBinding.familyDiseaseAndDrug.tvDisease.text =
                    if (it.diseaseList.isEmpty()) "해당 없음" else it.diseaseList.joinToString(", ")
                hospitalReportBinding.familyDiseaseAndDrug.tvDrug.text =
                    if (it.drugList.isEmpty()) "해당 없음" else it.drugList.joinToString(", ")
                hospitalReportBinding.familyDiseaseAndDrug.tvAllergy.text =
                    if (it.allergyList.isEmpty()) "해당 없음" else it.allergyList.joinToString(", ")
            }
        }

        // todo : 뷰모델 연결하기
        lifecycleScope.launch {
            viewModel.symptomContentByKorean.collectLatest {
                hospitalReportBinding.symptom.txtSymptom.text = it
            }
        }
        lifecycleScope.launch {
            viewModel.womenResponse.collectLatest {
                Timber.d("데이터 :$it")
                hospitalReportBinding.additionalInput.tvInput.text = it.additionalInput
            }
        }
        hospitalContentContainer.addView(hospitalReportBinding.root)
    }

    private fun setMapDataBinding() {
        // 지도로 이동 버튼 레이아웃 적용
        val contentContainer3 = binding.layoutGoToMap
        val goToMapBinding = LayoutHospitalTypeBinding.inflate(inflater, contentContainer3, false)

        goToMapBinding.tvHospitalType.text = getString(R.string.hospital_department_obgyn)
        goToMapBinding.btnGoToMap.text = getString(R.string.find_nearby_obgyn)

        goToMapBinding.btnGoToMap.setOnClickListener {
            val action = WomenSymptomFragmentDirections.actionWomenSymptomFragmentToMapFragment("산부")
            findNavController().navigate(action)
        }
        contentContainer3.addView(goToMapBinding.root)
    }

    override fun onBindLayout() {
        super.onBindLayout()

        // 건강 정보 결연결
        lifecycleScope.launch {
            viewModel.userHealthInfo.collectLatest {
                binding.layoutUserHealthInfo.tvFamilyDisease.text =
                    if (it.familyDiseaseList.isEmpty()) "해당 없음" else it.familyDiseaseList.joinToString(", ")
                binding.layoutUserHealthInfo.tvDisease.text = if (it.diseaseList.isEmpty()) "해당 없음" else it.diseaseList.joinToString(", ")
                binding.layoutUserHealthInfo.tvDrug.text = if (it.drugList.isEmpty()) "해당 없음" else it.drugList.joinToString(", ")
                binding.layoutUserHealthInfo.tvAllergy.text = if (it.allergyList.isEmpty()) "해당 없음" else it.allergyList.joinToString(", ")
            }
        }

        viewModel.getSymptom()
        inflater = LayoutInflater.from(requireContext())
        setCurrentSymptomBinding()
        setCurrentSymptomToHospital()
        setMapDataBinding()
        onSwitchClick()

        binding.btnBack.visibility = View.GONE
        binding.btnClose.setOnClickListener { navController.popBackStack() }
    }
}
