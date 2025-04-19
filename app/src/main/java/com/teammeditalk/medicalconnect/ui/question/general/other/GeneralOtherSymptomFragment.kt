package com.teammeditalk.medicalconnect.ui.question.inner.other

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentGeneralOtherSymptomBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import com.teammeditalk.medicalconnect.ui.util.ResourceUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GeneralOtherSymptomFragment :
    BaseFragment<FragmentGeneralOtherSymptomBinding>(
        FragmentGeneralOtherSymptomBinding::inflate,
    ) {
    private val viewModel: QuestionViewModel by activityViewModels()
    private val otherSymptomList = mutableListOf<String>()
    private val navController by lazy { findNavController() }
    private val otherByKorean = mutableListOf<String>()
    private var isSelected = false
    private var isOtherSymptom = false
    private val otherIdList = mutableListOf<String>()

    override fun onBindLayout() {
        super.onBindLayout()

        with(binding) {
            selectBoxYes.setOnClickListener {
                selectBoxYes.updateSelected(true)
                selectBoxNo.updateSelected(false)
                tvSymptomTitle.visibility = View.VISIBLE
                chipGroupSymptoms.visibility = View.VISIBLE
                isSelected = true
                isOtherSymptom = true
            }
            selectBoxNo.setOnClickListener {
                selectBoxYes.updateSelected(false)
                selectBoxNo.updateSelected(true)
                tvSymptomTitle.visibility = View.GONE
                chipGroupSymptoms.visibility = View.GONE
                isSelected = true
                isOtherSymptom = false
            }

            binding.btnBack.setOnClickListener { navController.popBackStack() }
            binding.btnNext.setOnClickListener {
                with(binding.chipGroupSymptoms) {
                    for (id in this.checkedChipIds) {
                        val chip = findViewById<Chip>(id)
                        otherSymptomList.add(chip.text.toString())
                        otherByKorean.add(ResourceUtil.getKoreanString(requireContext(), chip.tag.toString()))
                        otherIdList.add(chip.tag.toString())
                    }
                }

                if (isSelected) {
                    if (isOtherSymptom) {
                        // 동반 증상 - 예
                        if (otherSymptomList.isEmpty()) {
                            binding.warn.layoutLanguageWarn.visibility = View.VISIBLE
                        } else {
                            // 동반 증상이 있는 경우
                            binding.warn.layoutLanguageWarn.visibility = View.INVISIBLE
                            viewModel.setOtherListByKorean(otherByKorean)
                            viewModel.setOtherSymptomId(otherIdList)
                            viewModel.setOtherSymptomList(otherSymptomList)

                            val action =
                                GeneralOtherSymptomFragmentDirections.actionGeneralOtherSymptomFragmentToAdditionalInputFragment(
                                    "일반",
                                )
                            navController.navigate(action)
                        }
                    } else {
                        binding.warn.layoutLanguageWarn.visibility = View.INVISIBLE
                        viewModel.setOtherListByKorean(emptyList())
                        viewModel.setOtherSymptomId(emptyList())
                        viewModel.setOtherSymptomList(emptyList())

                        val action = GeneralOtherSymptomFragmentDirections.actionGeneralOtherSymptomFragmentToAdditionalInputFragment("일반")
                        navController.navigate(action)
                    }
                } else {
                    // 아직 아무것도 선택하지 않고 다음 버튼을 누르는 경우
                    binding.warn.layoutLanguageWarn.visibility = View.VISIBLE
                }
            }
        }
    }
}
