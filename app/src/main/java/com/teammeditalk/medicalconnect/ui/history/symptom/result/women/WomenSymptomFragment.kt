package com.teammeditalk.medicalconnect.ui.history.symptom.result.women

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
import com.teammeditalk.medicalconnect.databinding.LayoutWomenCurrentSymptomBinding
import dagger.hilt.android.AndroidEntryPoint

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
        currentSymptomFrame.addView(womenCurrentSymptomBinding.root)

        womenCurrentSymptomBinding.useWomenVM = true
        womenCurrentSymptomBinding.womenVM = viewModel
        womenCurrentSymptomBinding.lifecycleOwner = viewLifecycleOwner
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

        val currentSymptomBinding = LayoutWomenCurrentSymptomBinding.inflate(inflater, currentSymptomContainer, false)

        // todo : additional_input & 공툥 요소 데이터 바인딩 처리하기
        currentSymptomBinding.womenVM = viewModel
        currentSymptomBinding.lifecycleOwner = viewLifecycleOwner
        currentSymptomContainer.addView(currentSymptomBinding.root)

        hospitalReportBinding.symptom.womenVM = viewModel
        hospitalReportBinding.familyDiseaseAndDrug.womenVM = viewModel

        hospitalReportBinding.symptom.lifecycleOwner = viewLifecycleOwner
        hospitalReportBinding.familyDiseaseAndDrug.lifecycleOwner = viewLifecycleOwner
        hospitalReportBinding.symptom.lifecycleOwner = viewLifecycleOwner

        hospitalReportBinding.additionalInput.womenVM = viewModel
        hospitalReportBinding.additionalInput.lifecycleOwner = viewLifecycleOwner

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
