package com.teammeditalk.medicalconnect.ui.question.dental.result

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.LayoutCommonQuestionResultBinding
import com.teammeditalk.medicalconnect.databinding.LayoutDentalCurrentSymptomBinding
import com.teammeditalk.medicalconnect.databinding.LayoutDentalSideEffectInputBinding
import com.teammeditalk.medicalconnect.databinding.LayoutHospitalTypeBinding
import com.teammeditalk.medicalconnect.databinding.LayoutHospitalVersionQuestionResultBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class DentalSymptomResultFragment :
    BaseFragment<LayoutCommonQuestionResultBinding>(
        LayoutCommonQuestionResultBinding::inflate,
    ) {
    private lateinit var inflater: LayoutInflater
    private val viewModel: QuestionViewModel by activityViewModels()

    private fun onTabClick() {
        binding.tab.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when (tab?.position) {
                        0 -> {
                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    TODO("Not yet implemented")
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    TODO("Not yet implemented")
                }
            },
        )
    }

    private fun showSymptomResult() {
        lifecycleScope.launch {
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

    private fun setCurrentSymptomBinding() {
        // 사용자용 현재 증상 레이아웃 연결하기
        val currentSymptomFrame = binding.layoutFrame
        val dentalCurrentSymptomBinding = LayoutDentalCurrentSymptomBinding.inflate(inflater, currentSymptomFrame, false)
        currentSymptomFrame.addView(dentalCurrentSymptomBinding.root)

        dentalCurrentSymptomBinding.viewModel = viewModel
        dentalCurrentSymptomBinding.lifecycleOwner = viewLifecycleOwner
    }

    private fun setSideEffectBinding() {
        val sideEffectFrame = binding.layoutFrame2
        val sideEffectBindingToUser = LayoutDentalSideEffectInputBinding.inflate(inflater, sideEffectFrame, false)
        sideEffectFrame.addView(sideEffectBindingToUser.root)

        sideEffectBindingToUser.viewModel = viewModel
        sideEffectBindingToUser.lifecycleOwner = viewLifecycleOwner
    }

    private fun setHospitalVersionReport() {
        val hospitalContentContainer = binding.layoutHospitalVersion
        val hospitalReportBinding = LayoutHospitalVersionQuestionResultBinding.inflate(inflater, hospitalContentContainer, false)
        hospitalContentContainer.addView(hospitalReportBinding.root)
    }

    private fun setCurrentSymptomToHospital() {
        // 의료진용 보고서
        val hospitalContentContainer = binding.layoutHospitalVersion
        val hospitalReportBinding = LayoutHospitalVersionQuestionResultBinding.inflate(inflater, hospitalContentContainer, false)

        val currentSymptomContainer = hospitalReportBinding.layoutFrame
        val sideEffectContainer = hospitalReportBinding.layoutFrame2

        val currentSymptomBinding = LayoutDentalCurrentSymptomBinding.inflate(inflater, currentSymptomContainer, false)
        val sideEffectBinding = LayoutDentalSideEffectInputBinding.inflate(inflater, sideEffectContainer, false)

        currentSymptomBinding.viewModel = viewModel
        currentSymptomBinding.lifecycleOwner = viewLifecycleOwner
        currentSymptomContainer.addView(currentSymptomBinding.root)
        sideEffectContainer.addView(sideEffectBinding.root)

        hospitalReportBinding.symptom.viewModel = viewModel
        hospitalReportBinding.familyDiseaseAndDrug.viewModel = viewModel

        hospitalReportBinding.symptom.lifecycleOwner = viewLifecycleOwner
        hospitalReportBinding.familyDiseaseAndDrug.lifecycleOwner = viewLifecycleOwner
        hospitalReportBinding.symptom.lifecycleOwner = viewLifecycleOwner

        hospitalReportBinding.additionalInput.viewModel = viewModel
        hospitalReportBinding.additionalInput.lifecycleOwner = viewLifecycleOwner

        hospitalContentContainer.addView(hospitalReportBinding.root)
    }

    private fun setMapDataBinding() {
        // 지도로 이동 버튼 레이아웃 적용
        val contentContainer3 = binding.layoutGoToMap
        val goToMapBinding = LayoutHospitalTypeBinding.inflate(inflater, contentContainer3, false)

        goToMapBinding.tvHospitalType.text = getString(R.string.hospital_department_dental)
        goToMapBinding.btnGoToMap.text = getString(R.string.find_nearby_dental)

        goToMapBinding.btnGoToMap.setOnClickListener {
            val action = DentalSymptomResultFragmentDirections.actionDentalSymptomResultFragmentToMapFragment2("치과")
            findNavController().navigate(action)
        }
        contentContainer3.addView(goToMapBinding.root)
    }

    override fun onBindLayout() {
        super.onBindLayout()
        showSymptomResult()

        inflater = LayoutInflater.from(requireContext())

        setCurrentSymptomBinding()
        setSideEffectBinding()

        setHospitalVersionReport()
        setCurrentSymptomToHospital()
        setMapDataBinding()

        binding.layoutUserHealthInfo.viewModel = viewModel
        binding.layoutAdditionalInput.viewModel = viewModel

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

        lifecycleScope.launch {
            viewModel.saveDentalResponse()
        }

        binding.btnBack.setOnClickListener { findNavController().navigate(R.id.dentalAdditionalInputFragment) }
        binding.btnClose.setOnClickListener { findNavController().navigate(R.id.homeFragment2) }
    }
}
