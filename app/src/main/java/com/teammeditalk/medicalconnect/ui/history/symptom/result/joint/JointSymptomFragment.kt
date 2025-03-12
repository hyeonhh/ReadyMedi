package com.teammeditalk.medicalconnect.ui.history.symptom.result.joint

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.LayoutCommonQuestionResultBinding
import com.teammeditalk.medicalconnect.databinding.LayoutHospitalTypeBinding
import com.teammeditalk.medicalconnect.databinding.LayoutHospitalVersionGeneralBinding
import com.teammeditalk.medicalconnect.databinding.LayoutHospitalVersionQuestionResultBinding
import com.teammeditalk.medicalconnect.databinding.LayoutJointCurrentSymptomBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JointSymptomFragment :
    BaseFragment<LayoutCommonQuestionResultBinding>(
        LayoutCommonQuestionResultBinding::inflate,
    ) {
    private val viewModel: JointSymptomViewModel by viewModels()
    private val navController by lazy { findNavController() }
    private lateinit var inflater: LayoutInflater

    private fun setCurrentSymptomBinding() {
        // 사용자용 현재 증상 레이아웃 연결하기
        val currentSymptomFrame = binding.layoutFrame

        val jointCurrentSymptomBinding = LayoutJointCurrentSymptomBinding.inflate(inflater, currentSymptomFrame, false)
        currentSymptomFrame.addView(jointCurrentSymptomBinding.root)

        jointCurrentSymptomBinding.useJointVM = true
        jointCurrentSymptomBinding.jointVM = viewModel
        jointCurrentSymptomBinding.lifecycleOwner = viewLifecycleOwner
    }

    private fun setHospitalVersionReport() {
        val hospitalContentContainer = binding.layoutHospitalVersion
        val hospitalReportBinding = LayoutHospitalVersionGeneralBinding.inflate(inflater, hospitalContentContainer, false)
        hospitalContentContainer.addView(hospitalReportBinding.root)
    }

    private fun setCurrentSymptomToHospital() {
        // 의료진용 보고서
        val hospitalContentContainer = binding.layoutHospitalVersion
        val hospitalReportBinding = LayoutHospitalVersionQuestionResultBinding.inflate(inflater, hospitalContentContainer, false)

        val currentSymptomContainer = hospitalReportBinding.layoutFrame

        val currentSymptomBinding = LayoutJointCurrentSymptomBinding.inflate(inflater, currentSymptomContainer, false)

        // todo : additional_input & 공툥 요소 데이터 바인딩 처리하기
        currentSymptomBinding.jointVM = viewModel
        currentSymptomBinding.lifecycleOwner = viewLifecycleOwner
        currentSymptomContainer.addView(currentSymptomBinding.root)

        hospitalReportBinding.symptom.jointVM = viewModel
        hospitalReportBinding.familyDiseaseAndDrug.jointVM = viewModel

        hospitalReportBinding.symptom.lifecycleOwner = viewLifecycleOwner
        hospitalReportBinding.familyDiseaseAndDrug.lifecycleOwner = viewLifecycleOwner
        hospitalReportBinding.symptom.lifecycleOwner = viewLifecycleOwner

        hospitalReportBinding.additionalInput.jointVM = viewModel
        hospitalReportBinding.additionalInput.lifecycleOwner = viewLifecycleOwner

        hospitalContentContainer.addView(hospitalReportBinding.root)
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

    override fun onBindLayout() {
        super.onBindLayout()

        viewModel.getSymptom()
        inflater = LayoutInflater.from(requireContext())
        setCurrentSymptomBinding()
        setHospitalVersionReport()
        setCurrentSymptomToHospital()
        setMapDataBinding()

        binding.btnBack.visibility = View.GONE
        binding.btnClose.setOnClickListener { navController.popBackStack() }
    }
}
