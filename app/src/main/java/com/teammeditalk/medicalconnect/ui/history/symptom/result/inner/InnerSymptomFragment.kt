package com.teammeditalk.medicalconnect.ui.history.symptom.result.inner

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.HosCurrentSymptomInnerBinding
import com.teammeditalk.medicalconnect.databinding.LayoutCommonQuestionResultBinding
import com.teammeditalk.medicalconnect.databinding.LayoutHospitalTypeBinding
import com.teammeditalk.medicalconnect.databinding.LayoutHospitalVersionQuestionResultBinding
import com.teammeditalk.medicalconnect.databinding.LayoutInnerCurrentSymptomBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InnerSymptomFragment :
    BaseFragment<LayoutCommonQuestionResultBinding>(
        LayoutCommonQuestionResultBinding::inflate,
    ) {
    private val viewModel: InnerSymptomViewModel by viewModels()
    private val navController by lazy { findNavController() }
    private lateinit var inflater: LayoutInflater

    private fun setCurrentSymptomBinding() {
        // 사용자용 현재 증상 레이아웃 연결하기
        val currentSymptomFrame = binding.layoutFrame

        val innerCurrentSymptomBinding = LayoutInnerCurrentSymptomBinding.inflate(inflater, currentSymptomFrame, false)
        currentSymptomFrame.addView(innerCurrentSymptomBinding.root)

        innerCurrentSymptomBinding.useInnerVM = true
        innerCurrentSymptomBinding.innerVM = viewModel
        innerCurrentSymptomBinding.lifecycleOwner = viewLifecycleOwner
    }

    private fun setCurrentSymptomToHospital() {
        // 의료진용 보고서
        val hospitalContentContainer = binding.layoutHospitalVersion
        val hospitalReportBinding =
            LayoutHospitalVersionQuestionResultBinding.inflate(
                inflater,
                hospitalContentContainer,
                false,
            )

        // 개인 건강 정보
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // 건강 정보 연결
                launch {
                    viewModel.userHealthInfo.collectLatest {
                        hospitalReportBinding.familyDiseaseAndDrug.tvFamilyDisease.text =
                            if (it.familyDiseaseList.isEmpty()) {
                                getString(
                                    R.string.not_applicable,
                                )
                            } else {
                                it.familyDiseaseList.joinToString(", ")
                            }
                        hospitalReportBinding.familyDiseaseAndDrug.tvDisease.text =
                            if (it.diseaseList.isEmpty()) getString(R.string.not_applicable) else it.diseaseList.joinToString(", ")
                        hospitalReportBinding.familyDiseaseAndDrug.tvDrug.text =
                            if (it.drugList.isEmpty()) getString(R.string.not_applicable) else it.drugList.joinToString(", ")
                        hospitalReportBinding.familyDiseaseAndDrug.tvAllergy.text =
                            if (it.allergyList.isEmpty()) getString(R.string.not_applicable) else it.allergyList.joinToString(", ")
                    }
                }
                launch {
                    viewModel.symptomContentByKorean.collectLatest {
                        hospitalReportBinding.symptom.txtSymptom.text = it
                    }
                }
                launch {
                    viewModel.innerResponse.collectLatest {
                        hospitalReportBinding.additionalInput.tvInput.text = it.additionalInputByKorean
                        binding.layoutAdditionalInput.tvInput.text = it.additionalInput
                    }
                }
                launch {
                    viewModel.symptomContentByKorean.collectLatest {
                        hospitalReportBinding.symptom.txtSymptom.text = it
                    }
                }
            }
        }

        val currentSymptomContainer = hospitalReportBinding.layoutFrame

        val currentSymptomBinding =
            HosCurrentSymptomInnerBinding.inflate(inflater, currentSymptomContainer, false)

        // todo : additional_input & 공툥 요소 데이터 바인딩 처리하기
        currentSymptomBinding.useInnerVM = true
        currentSymptomBinding.innerVM = viewModel
        currentSymptomBinding.lifecycleOwner = viewLifecycleOwner

        currentSymptomContainer.addView(currentSymptomBinding.root)

        hospitalContentContainer.addView(hospitalReportBinding.root)
    }

    private fun setMapDataBinding() {
        // 지도로 이동 버튼 레이아웃 적용
        val contentContainer3 = binding.layoutGoToMap
        val goToMapBinding = LayoutHospitalTypeBinding.inflate(inflater, contentContainer3, false)

        goToMapBinding.tvHospitalType.text = getString(R.string.hospital_department_internal)
        goToMapBinding.btnGoToMap.text = getString(R.string.find_nearby_internal)

        goToMapBinding.btnGoToMap.setOnClickListener {
            val action = InnerSymptomFragmentDirections.actionInnerSymptomFragmentToMapFragment("내과")
            findNavController().navigate(action)
        }
        contentContainer3.addView(goToMapBinding.root)
    }

    // todo : 스위치 연결하기
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

    override fun onBindLayout() {
        super.onBindLayout()

        viewModel.getSymptom()
        inflater = LayoutInflater.from(requireContext())
        setCurrentSymptomBinding()
        setCurrentSymptomToHospital()
        setMapDataBinding()
        onSwitchClick()

        // 사용자 개인 정보
        lifecycleScope.launch {
            viewModel.userHealthInfo.collectLatest {
                with(binding) {
                    layoutUserHealthInfo.tvDrug.text = it.drugList.toString()
                    layoutUserHealthInfo.tvDisease.text = it.diseaseList.toString()
                    layoutUserHealthInfo.tvFamilyDisease.text = it.familyDiseaseList.toString()
                    layoutUserHealthInfo.tvAllergy.text = it.allergyList.toString()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.symptomContent.collectLatest {
                binding.layoutAdditionalInput.tvInput.text = it
            }
        }

        binding.btnBack.visibility = View.GONE
        binding.btnClose.setOnClickListener { navController.popBackStack() }
    }
}
