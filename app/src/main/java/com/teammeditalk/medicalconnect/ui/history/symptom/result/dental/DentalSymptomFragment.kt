package com.teammeditalk.medicalconnect.ui.history.symptom.result.dental

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.HosCurrentSymptomDentalBinding
import com.teammeditalk.medicalconnect.databinding.HosVersionSideEffectInputBinding
import com.teammeditalk.medicalconnect.databinding.LayoutCommonQuestionResultBinding
import com.teammeditalk.medicalconnect.databinding.LayoutDentalCurrentSymptomBinding
import com.teammeditalk.medicalconnect.databinding.LayoutDentalSideEffectInputBinding
import com.teammeditalk.medicalconnect.databinding.LayoutHospitalVersionQuestionResultBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DentalSymptomFragment :
    BaseFragment<LayoutCommonQuestionResultBinding>(
        LayoutCommonQuestionResultBinding::inflate,
    ) {
    private val viewModel: DentalSymptomViewModel by viewModels()
    private val navController by lazy { findNavController() }
    private lateinit var currentSymptomBinding: LayoutDentalCurrentSymptomBinding
    private lateinit var hospitalReportBinding: LayoutHospitalVersionQuestionResultBinding

    // todo : 스위치 연결하기
    private fun setHospitalVersionReport() {
        val inflater: LayoutInflater = LayoutInflater.from(requireContext())

        val hospitalContentContainer = binding.layoutHospitalVersion
        hospitalReportBinding = LayoutHospitalVersionQuestionResultBinding.inflate(inflater, hospitalContentContainer, false)

        val currentSymptomContainer = hospitalReportBinding.layoutFrame
        val sideEffectContainer = hospitalReportBinding.layoutFrame2

        val currentSymptomBinding = HosCurrentSymptomDentalBinding.inflate(inflater, currentSymptomContainer, false)
        val sideEffectBinding = HosVersionSideEffectInputBinding.inflate(inflater, sideEffectContainer, false)

        currentSymptomBinding.useDentalVM = true
        currentSymptomBinding.dentalVM = viewModel
        currentSymptomBinding.lifecycleOwner = viewLifecycleOwner

        sideEffectBinding.dentalVM = viewModel
        sideEffectBinding.useDentalVM = true
        sideEffectBinding.lifecycleOwner = viewLifecycleOwner

        currentSymptomContainer.addView(currentSymptomBinding.root)

        // 현재 증상 내용 연결
        lifecycleScope.launch {
            viewModel.symptomByKorean.collectLatest {
                hospitalReportBinding.symptom.txtSymptom.text = it
            }
        }

        // 건강 정보 연결
        lifecycleScope.launch {
            viewModel.userHealthInfo.collectLatest {
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

        lifecycleScope.launch {
            viewModel.dentalResponse.collectLatest {
                // todo :한국어로 번역하기
                hospitalReportBinding.additionalInput.tvInput.text = it.additionalInputByKorean
                binding.layoutAdditionalInput.tvInput.text = it.additionalInput
                sideEffectBinding.tvSideEffect.text = it.sideEffect
            }
        }
        sideEffectContainer.addView(sideEffectBinding.root)
        hospitalContentContainer.addView(hospitalReportBinding.root)
    }

    override fun onBindLayout() {
        super.onBindLayout()

        // 자식 프래그먼트의 콘텐츠 레이아웃 inflate
        val inflater: LayoutInflater = LayoutInflater.from(requireContext())

        val contentContainer = binding.layoutFrame
        val contentContainer2 = binding.layoutFrame2

        currentSymptomBinding =
            LayoutDentalCurrentSymptomBinding.inflate(inflater, contentContainer, false)
        setHospitalVersionReport()

        currentSymptomBinding.dentalVM = viewModel
        currentSymptomBinding.useDentalVM = true
        currentSymptomBinding.lifecycleOwner = viewLifecycleOwner

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

        lifecycleScope.launch {
            viewModel.userHealthInfo.collectLatest {
                with(binding.layoutUserHealthInfo) {
                    tvDisease.text =
                        if (it.diseaseList.isEmpty()) getString(R.string.not_applicable) else it.diseaseList.joinToString(",")
                    tvFamilyDisease.text =
                        if (it.familyDiseaseList.isEmpty()) {
                            getString(R.string.not_applicable)
                        } else {
                            it.familyDiseaseList.joinToString(
                                ",",
                            )
                        }
                    tvDrug.text =
                        if (it.drugList.isEmpty()) getString(R.string.not_applicable) else it.drugList.joinToString(",")
                    tvAllergy.text =
                        if (it.allergyList.isEmpty()) getString(R.string.not_applicable) else it.allergyList.joinToString(",")
                }
            }
        }

        binding.btnBack.visibility = View.GONE
        binding.btnClose.setOnClickListener { navController.popBackStack() }

        val sideEffectBindingToUser =
            LayoutDentalSideEffectInputBinding.inflate(inflater, contentContainer2, false)

        sideEffectBindingToUser.useDentalVM = true
        sideEffectBindingToUser.dentalVM = viewModel
        sideEffectBindingToUser.lifecycleOwner = viewLifecycleOwner

        lifecycleScope.launch {
            viewModel.dentalResponse.collectLatest {
                binding.layoutAdditionalInput.tvInput.text = it.additionalInput
                // sideEffectBindingToUser.tvSideEffect.text = it.sideEffect
            }
        }

        contentContainer.addView(currentSymptomBinding.root)
        contentContainer2.addView(sideEffectBindingToUser.root)
    }
}
