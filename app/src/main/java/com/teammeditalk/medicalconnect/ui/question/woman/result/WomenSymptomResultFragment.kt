package com.teammeditalk.medicalconnect.ui.question.woman.result

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.LayoutCommonQuestionResultBinding
import com.teammeditalk.medicalconnect.databinding.LayoutHospitalTypeBinding
import com.teammeditalk.medicalconnect.databinding.LayoutHospitalVersionWomenBinding
import com.teammeditalk.medicalconnect.databinding.LayoutWomenCurrentSymptomBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class WomenSymptomResultFragment :
    BaseFragment<LayoutCommonQuestionResultBinding>(
        LayoutCommonQuestionResultBinding::inflate,
    ) {
    private val viewModel: QuestionViewModel by activityViewModels()
    private lateinit var inflater: LayoutInflater

    private fun setCurrentSymptomBinding() {
        // 사용자용 현재 증상 레이아웃 연결하기
        val currentSymptomFrame = binding.layoutFrame
        val jointCurrentSymptomBinding = LayoutWomenCurrentSymptomBinding.inflate(inflater, currentSymptomFrame, false)
        currentSymptomFrame.addView(jointCurrentSymptomBinding.root)

        jointCurrentSymptomBinding.viewModel = viewModel
        jointCurrentSymptomBinding.lifecycleOwner = viewLifecycleOwner
    }

    private fun setHospitalVersionReport() {
        val hospitalContentContainer = binding.layoutHospitalVersion
        val hospitalReportBinding = LayoutHospitalVersionWomenBinding.inflate(inflater, hospitalContentContainer, false)

        hospitalReportBinding.currentSymptom.viewModel = viewModel
        hospitalReportBinding.currentSymptom.lifecycleOwner = viewLifecycleOwner

        hospitalReportBinding.symptom.viewModel = viewModel
        hospitalReportBinding.symptom.lifecycleOwner = viewLifecycleOwner

        hospitalReportBinding.familyDiseaseAndDrug.viewModel = viewModel
        hospitalReportBinding.familyDiseaseAndDrug.lifecycleOwner = viewLifecycleOwner

        hospitalReportBinding.additionalInput.viewModel = viewModel
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
            val action = WomenSymptomResultFragmentDirections.actionWomenSymptomResultFragmentToMapFragment6("일반")
            findNavController().navigate(action)
        }
        contentContainer3.addView(goToMapBinding.root)
    }

    private fun showSymptomResult() {
        lifecycleScope.launch {
            viewModel.additionalInput.collectLatest {
                val options =
                    TranslatorOptions
                        .Builder()
                        .setSourceLanguage(TranslateLanguage.KOREAN)
                        .setTargetLanguage(TranslateLanguage.ENGLISH)
                        .build()
                val koreanEnglishTranslator = Translation.getClient(options)

                koreanEnglishTranslator
                    .translate(it)
                    .addOnSuccessListener {
                        binding.layoutAdditionalInput.tvInput.text = it
                    }.addOnFailureListener { exception ->
                        exception.printStackTrace()
                        Timber.d("Failed to translate :${exception.message}")
                    }
            }
        }

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
    }

    override fun onBindLayout() {
        super.onBindLayout()

        inflater = LayoutInflater.from(requireContext())
        setCurrentSymptomBinding()

        setHospitalVersionReport()
        setMapDataBinding()

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

        binding.btnBack.setOnClickListener {
            val action = WomenSymptomResultFragmentDirections.actionWomenSymptomResultFragmentToMapFragment6("산부인과")
            findNavController().navigate(action)
        }
        binding.btnClose.setOnClickListener {
            requireActivity().finish()
        }
        showSymptomResult()
        viewModel.saveWomenResponse()
    }
}
