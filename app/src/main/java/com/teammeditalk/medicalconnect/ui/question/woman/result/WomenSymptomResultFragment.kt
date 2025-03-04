package com.teammeditalk.medicalconnect.ui.question.woman.result

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentWomenSymptomResultBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WomenSymptomResultFragment :
    BaseFragment<FragmentWomenSymptomResultBinding>(
        FragmentWomenSymptomResultBinding::inflate,
    ) {
    private val viewModel: QuestionViewModel by activityViewModels()

    private fun showSymptomResult() {
        lifecycleScope.launch {
            viewModel.additionalInput.collectLatest {
                binding.layoutAdditionalInput.tvInput.text = it
            }
        }
        lifecycleScope.launch {
            viewModel.selectedSymptom.collectLatest {
                binding.layoutCurrentSymptom.tvSymptomTitle.text = it.first
                binding.layoutCurrentSymptom.tvSymptomContent.text = it.second
            }
        }
        lifecycleScope.launch {
            viewModel.selectedDate.collectLatest {
                binding.layoutCurrentSymptom.tvSymptomStartDate.text = it
            }
        }

        lifecycleScope.launch {
            viewModel.selectedDegree.collectLatest {
                binding.layoutCurrentSymptom.tvSymptomDegree.text = it.toString()
            }
        }

        lifecycleScope.launch {
            viewModel.selectedType.collectLatest {
                binding.layoutCurrentSymptom.tvSymptomType.text = it.toString()
            }
        }

        lifecycleScope.launch {
            viewModel.selectedWorseList.collectLatest {
                binding.layoutCurrentSymptom.tvSymptomWorseList.text = it.toString()
            }
        }
        lifecycleScope.launch {
//            viewModel.selectedOtherList.collectLatest {
//                binding.layoutCurrentSymptom.tvOtherSymptom.text = it.toString()
//            }
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

        lifecycleScope.launch {
            viewModel.selectedWomenLastDate.collectLatest {
                binding.layoutCurrentSymptom.tvLastPeriod.text = it
            }
        }
        lifecycleScope.launch {
            viewModel.selectedIsAvailablePregnancy.collectLatest {
                binding.layoutCurrentSymptom.tvPregnancy.text = it
            }
        }
        lifecycleScope.launch {
            viewModel.selectedRegularity.collectLatest {
                binding.layoutCurrentSymptom.tvReguarlity.text = it
            }
        }
    }

    override fun onBindLayout() {
        super.onBindLayout()
        showSymptomResult()
        viewModel.saveWomenResponse()
    }
}
