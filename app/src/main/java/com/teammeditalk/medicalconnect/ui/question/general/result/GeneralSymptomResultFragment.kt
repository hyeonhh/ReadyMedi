package com.teammeditalk.medicalconnect.ui.question.general.result

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.LayoutCommonQuestionResultBinding
import com.teammeditalk.medicalconnect.databinding.LayoutGeneralCurrentSymptomBinding
import com.teammeditalk.medicalconnect.databinding.LayoutHospitalTypeBinding
import com.teammeditalk.medicalconnect.databinding.LayoutHospitalVersionGeneralBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GeneralSymptomResultFragment :
    BaseFragment<LayoutCommonQuestionResultBinding>(
        LayoutCommonQuestionResultBinding::inflate,
    ) {
    private val viewModel: QuestionViewModel by activityViewModels()
    private lateinit var inflater: LayoutInflater

    private fun setCurrentSymptomBinding() {
        // 사용자용 현재 증상 레이아웃 연결하기
        val currentSymptomFrame = binding.layoutFrame
        val generalCurrentSymptomBinding = LayoutGeneralCurrentSymptomBinding.inflate(inflater, currentSymptomFrame, false)
        currentSymptomFrame.addView(generalCurrentSymptomBinding.root)

        generalCurrentSymptomBinding.questionVM = viewModel
        generalCurrentSymptomBinding.lifecycleOwner = viewLifecycleOwner
    }

    private fun setHospitalVersionReport() {
        val hospitalContentContainer = binding.layoutHospitalVersion
        val hospitalReportBinding = LayoutHospitalVersionGeneralBinding.inflate(inflater, hospitalContentContainer, false)

        hospitalReportBinding.symptom.viewModel = viewModel
        hospitalReportBinding.symptom.lifecycleOwner = viewLifecycleOwner

        hospitalReportBinding.familyDiseaseAndDrug.viewModel = viewModel
        hospitalReportBinding.familyDiseaseAndDrug.lifecycleOwner = viewLifecycleOwner

        hospitalReportBinding.currentSymptom.questionVM = viewModel
        hospitalReportBinding.currentSymptom.lifecycleOwner = viewLifecycleOwner

        hospitalReportBinding.additionalInput.viewModel = viewModel
        hospitalReportBinding.additionalInput.lifecycleOwner = viewLifecycleOwner

        hospitalContentContainer.addView(hospitalReportBinding.root)
    }

    private fun setMapDataBinding() {
        // 지도로 이동 버튼 레이아웃 적용
        val contentContainer3 = binding.layoutGoToMap
        val goToMapBinding = LayoutHospitalTypeBinding.inflate(inflater, contentContainer3, false)

        goToMapBinding.tvHospitalType.text = getString(R.string.hospital_department_general)
        goToMapBinding.btnGoToMap.text = getString(R.string.find_nearby_general)

        goToMapBinding.btnGoToMap.setOnClickListener {
            val action = GeneralSymptomResultFragmentDirections.actionGeneralSymptomResultFragmentToMapFragment3("일반")
            findNavController().navigate(action)
        }
        contentContainer3.addView(goToMapBinding.root)
    }

    private fun setAdditionalLayout() {
        binding.layoutAdditionalInput.viewModel = viewModel
        binding.layoutAdditionalInput.lifecycleOwner = viewLifecycleOwner
    }

    override fun onBindLayout() {
        super.onBindLayout()

        inflater = LayoutInflater.from(requireContext())
        setCurrentSymptomBinding()
        setHospitalVersionReport()
        setMapDataBinding()
        setAdditionalLayout()

        binding.layoutUserHealthInfo.viewModel = viewModel
        binding.layoutUserHealthInfo.lifecycleOwner = viewLifecycleOwner

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

        binding.btnBack.setOnClickListener { findNavController().navigate(R.id.generalAdditionalInputFragment) }
        binding.btnClose.setOnClickListener {
            requireActivity().finish()
        }
        viewModel.saveGeneralResponse()
    }
}
