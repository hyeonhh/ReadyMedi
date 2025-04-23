package com.teammeditalk.medicalconnect.ui.question.joint.result

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.LayoutCommonQuestionResultBinding
import com.teammeditalk.medicalconnect.databinding.LayoutHospitalTypeBinding
import com.teammeditalk.medicalconnect.databinding.LayoutHospitalVersionJointBinding
import com.teammeditalk.medicalconnect.databinding.LayoutJointCurrentSymptomBinding
import com.teammeditalk.medicalconnect.databinding.LayoutJointHistoryBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentJointSymptomResult :
    BaseFragment<LayoutCommonQuestionResultBinding>(
        LayoutCommonQuestionResultBinding::inflate,
    ) {
    private val viewModel: QuestionViewModel by activityViewModels()
    private lateinit var inflater: LayoutInflater

    private fun setCurrentSymptomBinding() {
        // 사용자용 현재 증상 레이아웃 연결하기
        val currentSymptomFrame = binding.layoutFrame

        val jointCurrentSymptomBinding = LayoutJointCurrentSymptomBinding.inflate(inflater, currentSymptomFrame, false)

        val historyBinding = LayoutJointHistoryBinding.inflate(inflater, binding.layoutFrame2, false)

        jointCurrentSymptomBinding.viewModel = viewModel
        jointCurrentSymptomBinding.lifecycleOwner = viewLifecycleOwner

        currentSymptomFrame.addView(jointCurrentSymptomBinding.root)

        historyBinding.viewModel = viewModel
        historyBinding.lifecycleOwner = viewLifecycleOwner

        binding.layoutFrame2.addView(historyBinding.root)
    }

    private fun setHospitalVersionReport() {
        val hospitalContentContainer = binding.layoutHospitalVersion
        val hospitalReportBinding = LayoutHospitalVersionJointBinding.inflate(inflater, hospitalContentContainer, false)

        hospitalReportBinding.familyDiseaseAndDrug.viewModel = viewModel
        hospitalReportBinding.familyDiseaseAndDrug.lifecycleOwner = viewLifecycleOwner

        hospitalReportBinding.currentSymptom.viewModel = viewModel
        hospitalReportBinding.currentSymptom.lifecycleOwner = viewLifecycleOwner

        hospitalReportBinding.additionalInput.viewModel = viewModel
        hospitalReportBinding.additionalInput.lifecycleOwner = viewLifecycleOwner

        hospitalReportBinding.symptom.viewModel = viewModel
        hospitalReportBinding.symptom.lifecycleOwner = viewLifecycleOwner

        hospitalReportBinding.injury.viewModel = viewModel
        hospitalReportBinding.injury.lifecycleOwner = viewLifecycleOwner

        hospitalContentContainer.addView(hospitalReportBinding.root)
    }

    private fun setMapDataBinding() {
        // 지도로 이동 버튼 레이아웃 적용
        val contentContainer3 = binding.layoutGoToMap
        val goToMapBinding = LayoutHospitalTypeBinding.inflate(inflater, contentContainer3, false)

        goToMapBinding.tvHospitalType.text = getString(R.string.hospital_department_orthopedic)
        goToMapBinding.btnGoToMap.text = getString(R.string.find_nearby_orthopedic)

        goToMapBinding.btnGoToMap.setOnClickListener {
            val action = FragmentJointSymptomResultDirections.actionFragmentJointSymptomResultToMapFragment5("일반")
            findNavController().navigate(action)
        }
        contentContainer3.addView(goToMapBinding.root)
    }

    private fun setHealthInfo() {
        binding.layoutUserHealthInfo.viewModel = viewModel
        binding.layoutUserHealthInfo.lifecycleOwner = viewLifecycleOwner
    }

    private fun setAdditionalInput() {
        binding.layoutAdditionalInput.viewModel = viewModel
        binding.layoutAdditionalInput.lifecycleOwner = viewLifecycleOwner
    }

    override fun onBindLayout() {
        super.onBindLayout()

        inflater = LayoutInflater.from(requireContext())
        setCurrentSymptomBinding()
        setHealthInfo()
        setAdditionalInput()

        setHospitalVersionReport()
        setMapDataBinding()

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

        binding.btnBack.setOnClickListener { findNavController().navigate(R.id.jointAdditionalInputFragment) }
        binding.btnClose.setOnClickListener {
            requireActivity().finish()
        }
        viewModel.saveJointResponse()
    }
}
