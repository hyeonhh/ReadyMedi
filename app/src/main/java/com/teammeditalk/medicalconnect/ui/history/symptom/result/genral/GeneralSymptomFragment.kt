package com.teammeditalk.medicalconnect.ui.history.symptom.result.genral

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.LayoutCommonQuestionResultBinding
import com.teammeditalk.medicalconnect.databinding.LayoutGeneralCurrentSymptomBinding
import com.teammeditalk.medicalconnect.databinding.LayoutHospitalTypeBinding
import com.teammeditalk.medicalconnect.databinding.LayoutHospitalVersionGeneralBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GeneralSymptomFragment :
    BaseFragment<LayoutCommonQuestionResultBinding>(
        LayoutCommonQuestionResultBinding::inflate,
    ) {
    private val viewModel: GeneralSymptomViewModel by viewModels()
    private val navController by lazy { findNavController() }
    private lateinit var inflater: LayoutInflater
    private lateinit var hospitalReportBinding: LayoutHospitalVersionGeneralBinding

    private fun setCurrentSymptomBinding() {
        // 사용자용 현재 증상 레이아웃 연결하기
        val currentSymptomFrame = binding.layoutFrame

        val generalCurrentSymptomBinding = LayoutGeneralCurrentSymptomBinding.inflate(inflater, currentSymptomFrame, false)
        currentSymptomFrame.addView(generalCurrentSymptomBinding.root)

        generalCurrentSymptomBinding.useGeneralVM = true
        generalCurrentSymptomBinding.generalVM = viewModel
        generalCurrentSymptomBinding.lifecycleOwner = viewLifecycleOwner
    }

    private fun setHospitalVersionReport() {
        val hospitalContentContainer = binding.layoutHospitalVersion
        hospitalReportBinding = LayoutHospitalVersionGeneralBinding.inflate(inflater, hospitalContentContainer, false)

        hospitalReportBinding.symptom.useGeneralVM = true
        hospitalReportBinding.symptom.lifecycleOwner = viewLifecycleOwner

        hospitalReportBinding.symptom.generalVM = viewModel
        hospitalReportBinding.familyDiseaseAndDrug.generalVM = viewModel

        hospitalReportBinding.familyDiseaseAndDrug.lifecycleOwner = viewLifecycleOwner

        hospitalReportBinding.currentSymptom.useGeneralVM = true
        hospitalReportBinding.currentSymptom.generalVM = viewModel
        hospitalReportBinding.currentSymptom.lifecycleOwner = viewLifecycleOwner

        // todo : additional input 뷰모델 연결하기 !
        lifecycleScope.launch {
            viewModel.generalResponse.collectLatest {
                hospitalReportBinding.additionalInput.tvInput.text = it.additionalInputByKorean
            }
        }

        lifecycleScope.launch {
            viewModel.symptomByKorean.collectLatest {
                hospitalReportBinding.symptom.txtSymptom.text = it
            }
        }

        lifecycleScope.launch {
            viewModel.userHealthInfo.collectLatest {
                with(binding) {
                    hospitalReportBinding.familyDiseaseAndDrug.tvDrug.text = it.drugList.joinToString(", ")
                    hospitalReportBinding.familyDiseaseAndDrug.tvDisease.text = it.diseaseList.joinToString(", ")
                    hospitalReportBinding.familyDiseaseAndDrug.tvFamilyDisease.text = it.familyDiseaseList.joinToString(", ")
                    hospitalReportBinding.familyDiseaseAndDrug.tvAllergy.text = it.allergyList.joinToString(", ")
                }
            }
        }

        hospitalContentContainer.addView(hospitalReportBinding.root)
    }

    private fun setMapDataBinding() {
        // 지도로 이동 버튼 레이아웃 적용
        val contentContainer3 = binding.layoutGoToMap
        val goToMapBinding = LayoutHospitalTypeBinding.inflate(inflater, contentContainer3, false)

        goToMapBinding.tvHospitalType.text = getString(R.string.hospital_department_general)
        goToMapBinding.btnGoToMap.text = getString(R.string.find_nearby_general)

        goToMapBinding.btnGoToMap.setOnClickListener {
            val action = GeneralSymptomFragmentDirections.actionGeneralSymptomFragmentToMapFragment("일반")
            findNavController().navigate(action)
        }
        contentContainer3.addView(goToMapBinding.root)
    }

    override fun onBindLayout() {
        super.onBindLayout()
        // val ddun = "i_love_you_ddun"

        binding.btnSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.ivTooltip.visibility = View.INVISIBLE
            if (isChecked) {
                binding.layoutHospitalVersion.visibility = View.VISIBLE
                binding.layoutUser.visibility = View.GONE
            } else {
                binding.layoutHospitalVersion.visibility = View.GONE
                binding.layoutUser.visibility = View.VISIBLE
            }
        }

        viewModel.getSymptom()
        inflater = LayoutInflater.from(requireContext())
        setCurrentSymptomBinding()
        setHospitalVersionReport()
        // setCurrentSymptomToHospital()
        setMapDataBinding()

        // 사용자 개인 정보
        lifecycleScope.launch {
            viewModel.userHealthInfo.collectLatest {
                with(binding) {
                    layoutUserHealthInfo.tvDrug.text = it.drugList.joinToString(", ")
                    layoutUserHealthInfo.tvDisease.text = it.diseaseList.joinToString(", ")
                    layoutUserHealthInfo.tvFamilyDisease.text = it.familyDiseaseList.joinToString(", ")
                    layoutUserHealthInfo.tvAllergy.text = it.allergyList.joinToString(", ")
                }
            }
        }

        binding.btnBack.visibility = View.GONE
        binding.btnClose.setOnClickListener { navController.popBackStack() }
    }
}
