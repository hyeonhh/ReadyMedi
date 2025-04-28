package com.teammeditalk.medicalconnect.ui.history.symptom.result.joint

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.LayoutCommonQuestionResultBinding
import com.teammeditalk.medicalconnect.databinding.LayoutHospitalTypeBinding
import com.teammeditalk.medicalconnect.databinding.LayoutHospitalVersionJointBinding
import com.teammeditalk.medicalconnect.databinding.LayoutJointCurrentSymptomBinding
import com.teammeditalk.medicalconnect.databinding.LayoutJointHistoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class JointSymptomFragment :
    BaseFragment<LayoutCommonQuestionResultBinding>(
        LayoutCommonQuestionResultBinding::inflate,
    ) {
    private val viewModel: JointSymptomViewModel by viewModels()
    private val navController by lazy { findNavController() }
    private lateinit var inflater: LayoutInflater
    private lateinit var hospitalReportBinding: LayoutHospitalVersionJointBinding

    private fun setInjuryHistoryBinding() {
        val frame = binding.layoutFrame2

        val injuryHistoryBinding = LayoutJointHistoryBinding.inflate(inflater, frame, false)

        injuryHistoryBinding.jointVM = viewModel
        injuryHistoryBinding.lifecycleOwner = viewLifecycleOwner
        injuryHistoryBinding.useJointVM = true

        frame.addView(injuryHistoryBinding.root)
    }

    private fun setCurrentSymptomBinding() {
        // 사용자용 현재 증상 레이아웃 연결하기
        val currentSymptomFrame = binding.layoutFrame

        val jointCurrentSymptomBinding = LayoutJointCurrentSymptomBinding.inflate(inflater, currentSymptomFrame, false)

        jointCurrentSymptomBinding.useJointVM = true
        jointCurrentSymptomBinding.jointVM = viewModel
        jointCurrentSymptomBinding.lifecycleOwner = viewLifecycleOwner
        currentSymptomFrame.addView(jointCurrentSymptomBinding.root)
    }

    private fun setCurrentSymptomToHospital() {
        // 의료진용 보고서
        val hospitalContentContainer = binding.layoutHospitalVersion
        hospitalReportBinding = LayoutHospitalVersionJointBinding.inflate(inflater, hospitalContentContainer, false)

        hospitalReportBinding.currentSymptom.jointVM = viewModel
        hospitalReportBinding.currentSymptom.useJointVM = true
        hospitalReportBinding.currentSymptom.lifecycleOwner = viewLifecycleOwner

        // todo : additional_input & 공툥 요소 데이터 바인딩 처리하기

        hospitalReportBinding.familyDiseaseAndDrug.jointVM = viewModel
        hospitalReportBinding.familyDiseaseAndDrug.lifecycleOwner = viewLifecycleOwner

        hospitalReportBinding.injury.useJointVM = true
        hospitalReportBinding.injury.jointVM = viewModel
        hospitalReportBinding.injury.lifecycleOwner = viewLifecycleOwner

        // todo : 뷰모델 연결하기

        lifecycleScope.launch {
            viewModel.jointResponse.collectLatest {
                hospitalReportBinding.additionalInput.tvInput.text = it.additionalInputByKorean
                binding.layoutAdditionalInput.tvInput.text = it.additionalInput
            }
        }

        lifecycleScope.launch {
            viewModel.symptomContent.collectLatest {
                hospitalReportBinding.symptom.txtSymptom.text = it
            }
        }

        hospitalContentContainer.addView(hospitalReportBinding.root)
    }

    private fun setUserHealthInfoHos() {
        // 건강 정보 연결
        lifecycleScope.launch {
            viewModel.userHealthInfoByKorean.collectLatest {
                hospitalReportBinding.familyDiseaseAndDrug.tvFamilyDisease.text =
                    if (it.familyDiseaseList.isEmpty()) getString(R.string.not_applicable) else it.familyDiseaseList.joinToString(", ")
                hospitalReportBinding.familyDiseaseAndDrug.tvDisease.text =
                    if (it.diseaseList.isEmpty()) getString(R.string.not_applicable) else it.diseaseList.joinToString(", ")
                hospitalReportBinding.familyDiseaseAndDrug.tvDrug.text =
                    if (it.drugList.isEmpty()) getString(R.string.not_applicable) else it.drugList.joinToString(", ")
                hospitalReportBinding.familyDiseaseAndDrug.tvAllergy.text =
                    if (it.allergyList.isEmpty()) getString(R.string.not_applicable) else it.allergyList.joinToString(", ")
            }
        }
    }

    private fun setMapDataBinding() {
        // 지도로 이동 버튼 레이아웃 적용
        val contentContainer3 = binding.layoutGoToMap
        val goToMapBinding = LayoutHospitalTypeBinding.inflate(inflater, contentContainer3, false)

        goToMapBinding.tvHospitalType.text = getString(R.string.hospital_department_orthopedic)
        goToMapBinding.btnGoToMap.text = getString(R.string.find_nearby_orthopedic)

        goToMapBinding.btnGoToMap.setOnClickListener {
            val action = JointSymptomFragmentDirections.actionJointSymptomFragmentToMapFragment("정형")
            findNavController().navigate(action)
        }
        contentContainer3.addView(goToMapBinding.root)
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

    private fun setHealthInfo() {
        lifecycleScope.launch {
            viewModel.userHealthInfo.collectLatest {
                binding.layoutUserHealthInfo.tvFamilyDisease.text =
                    if (it.familyDiseaseList.isEmpty()) "해당 없음" else it.familyDiseaseList.joinToString(", ")
                binding.layoutUserHealthInfo.tvDisease.text =
                    if (it.diseaseList.isEmpty()) "해당 없음" else it.diseaseList.joinToString(", ")
                binding.layoutUserHealthInfo.tvDrug.text =
                    if (it.drugList.isEmpty()) "해당 없음" else it.drugList.joinToString(", ")
                binding.layoutUserHealthInfo.tvAllergy.text =
                    if (it.allergyList.isEmpty()) "해당 없음" else it.allergyList.joinToString(", ")
            }
        }
    }

    override fun onBindLayout() {
        super.onBindLayout()

        viewModel.getSymptom()
        inflater = LayoutInflater.from(requireContext())

        setInjuryHistoryBinding()
        setCurrentSymptomBinding()
        setCurrentSymptomToHospital()
        setMapDataBinding()
        onSwitchClick()
        setHealthInfo()
        setUserHealthInfoHos()

        binding.btnBack.visibility = View.GONE
        binding.btnClose.setOnClickListener { navController.popBackStack() }
    }
}
