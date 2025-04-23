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

@AndroidEntryPoint
class WomenSymptomFragment :
    BaseFragment<LayoutCommonQuestionResultBinding>(
        LayoutCommonQuestionResultBinding::inflate,
    ) {
    private val viewModel: WomenSymptomViewModel by viewModels()
    private val navController by lazy { findNavController() }
    private lateinit var inflater: LayoutInflater
    private lateinit var hospitalReportBinding: LayoutHospitalVersionWomenBinding

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
        hospitalReportBinding = LayoutHospitalVersionWomenBinding.inflate(inflater, hospitalContentContainer, false)

        // todo : additional_input & 공툥 요소 데이터 바인딩 처리하기
        hospitalReportBinding.currentSymptom.useWomenVM = true
        hospitalReportBinding.currentSymptom.womenVM = viewModel
        hospitalReportBinding.currentSymptom.lifecycleOwner = viewLifecycleOwner

        // todo : 뷰모델 연결하기
        lifecycleScope.launch {
            viewModel.symptomContentByKorean.collectLatest {
                hospitalReportBinding.symptom.txtSymptom.text = it
            }
        }
        lifecycleScope.launch {
            viewModel.womenResponse.collectLatest {
                hospitalReportBinding.additionalInput.tvInput.text = it.additionalInputByKorean
                binding.layoutAdditionalInput.tvInput.text = it.additionalInput
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

    private fun setUserHealthInfo() {
        // 건강 정보 결연결
        lifecycleScope.launch {
            viewModel.userHealthInfo.collectLatest {
                val familyDiseaseList =
                    if (it.familyDiseaseList.isEmpty()) {
                        getString(
                            R.string.not_applicable,
                        )
                    } else {
                        it.familyDiseaseList.joinToString(", ")
                    }

                val diseaseList = if (it.diseaseList.isEmpty()) getString(R.string.not_applicable) else it.diseaseList.joinToString(", ")
                val drugList = if (it.drugList.isEmpty()) getString(R.string.not_applicable) else it.drugList.joinToString(", ")
                val allergyList = if (it.allergyList.isEmpty()) getString(R.string.not_applicable) else it.allergyList.joinToString(", ")

                binding.layoutUserHealthInfo.tvFamilyDisease.text = familyDiseaseList
                hospitalReportBinding.familyDiseaseAndDrug.tvFamilyDisease.text = familyDiseaseList

                binding.layoutUserHealthInfo.tvDisease.text = diseaseList
                hospitalReportBinding.familyDiseaseAndDrug.tvDisease.text = diseaseList

                binding.layoutUserHealthInfo.tvDrug.text = drugList
                hospitalReportBinding.familyDiseaseAndDrug.tvDrug.text = drugList

                binding.layoutUserHealthInfo.tvAllergy.text = allergyList
                hospitalReportBinding.familyDiseaseAndDrug.tvAllergy.text = allergyList
            }
        }
    }

    override fun onBindLayout() {
        super.onBindLayout()

        viewModel.getSymptom()
        inflater = LayoutInflater.from(requireContext())
        setCurrentSymptomBinding()
        setCurrentSymptomToHospital()
        setUserHealthInfo()
        setMapDataBinding()
        onSwitchClick()

        binding.btnBack.visibility = View.GONE
        binding.btnClose.setOnClickListener { navController.popBackStack() }
    }
}
