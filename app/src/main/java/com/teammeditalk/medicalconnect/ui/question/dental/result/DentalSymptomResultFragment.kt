package com.teammeditalk.medicalconnect.ui.question.dental.fragment.result

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.createBalloon
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentDentalSymtomResultBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DentalSymptomResultFragment :
    BaseFragment<FragmentDentalSymtomResultBinding>(
        FragmentDentalSymtomResultBinding::inflate,
    ) {
    private val viewModel: QuestionViewModel by activityViewModels()

    private fun showToolTip() {
        val balloon =
            context?.let {
                createBalloon(it) {
                    setText("의료진에게 보여줄 땐 여길 눌러주세요")
                    setMarginRight(20)
                    setPadding(10)
                    setHeight(BalloonSizeSpec.WRAP)
                    setWidth(BalloonSizeSpec.WRAP)
                    setTextColorResource(R.color.white)
                    setCornerRadius(30f)
                    setBackgroundColorResource(R.color.orange50)
                    build()
                }
            }
        balloon?.showAlignTop(binding.btnSwitch)
    }

    private fun showSymptomResult() {
        lifecycleScope.launch {
            viewModel.additionalInput.collectLatest {
                binding.layoutAdditionalInput.tvInput.text = it
            }
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
            viewModel.selectedOtherList.collectLatest {
                binding.layoutCurrentSymptom.tvOtherSymptom.text = it.toString()
            }
        }
        lifecycleScope.launch {
            viewModel.anesthesiaHistory.collectLatest {
                binding.layoutCurrentSymptom.tvSymptomAnesHistory.text = it.toString()
            }
        }

        lifecycleScope.launch {
            viewModel.sideEffect.collectLatest {
                binding.layoutDentalSideEffectInput.tvSideEffect.text = it
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
        showToolTip()
        showSymptomResult()
        lifecycleScope.launch {
            viewModel.saveDentalResponse()
        }
    }
}
