package com.teammeditalk.medicalconnect.ui.question.woman.result

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentWomenSymptomResultBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class WomenSymptomResultFragment :
    BaseFragment<FragmentWomenSymptomResultBinding>(
        FragmentWomenSymptomResultBinding::inflate,
    ) {
    private val viewModel: QuestionViewModel by activityViewModels()

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
            viewModel.selectedWorseList.collectLatest {
                binding.layoutCurrentSymptom.tvSymptomWorseList.text = it.toString()
            }
        }
        lifecycleScope.launch {
            viewModel.selectedOtherList.collectLatest {
                binding.layoutCurrentSymptom.tvSymptomOtherList.text = it.toString()
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

        binding.layoutCurrentSymptom.viewModel = viewModel
        binding.layoutCurrentSymptom.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.btnSwitch.setOnCheckedChangeListener { view, isChecked ->
            if (isChecked) {
                binding.layout.visibility = View.GONE
                binding.layoutHospitalVersion.visibility = View.VISIBLE
            } else {
                binding.layout.visibility = View.VISIBLE
                binding.layoutHospitalVersion.visibility = View.GONE
            }
        }
        binding.layoutWomenHospitalType.btnGoToMap.setOnClickListener {
            findNavController().navigate(R.id.action_womenSymptomResultFragment_to_mapFragment6)
        }
        binding.btnBack.setOnClickListener {
            val action = WomenSymptomResultFragmentDirections.actionWomenSymptomResultFragmentToMapFragment6("산부인과")
            findNavController().navigate(action)
        }
        binding.btnClose.setOnClickListener { findNavController().navigate(R.id.homeFragment) }
        showSymptomResult()
        viewModel.saveWomenResponse()
    }
}
