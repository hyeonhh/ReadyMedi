package com.teammeditalk.medicalconnect.ui.question.dental.fragment.other

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentSelectOtherDentalSymptomBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import com.teammeditalk.medicalconnect.ui.util.ResourceUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DentalSelectOtherSymptomFragment :
    BaseFragment<FragmentSelectOtherDentalSymptomBinding>(
        FragmentSelectOtherDentalSymptomBinding::inflate,
    ) {
    private val viewModel: QuestionViewModel by activityViewModels()
    private val navController by lazy { findNavController() }
    private var selected: Boolean = false
    private var selectedList: MutableList<String> = mutableListOf()
    private var selectedChipIdList: MutableList<Int> = mutableListOf()
    private var otherListByKorean = mutableListOf<String>()

    private val idList = mutableListOf<String>()

    override fun onBindLayout() {
        super.onBindLayout()

        with(binding) {
            btnBack.setOnClickListener { navController.popBackStack() }
            btnNext.setOnClickListener {
                if (!selected) {
                    binding.layoutLanguageWarn.layoutLanguageWarn.visibility = View.VISIBLE
                } else {
                    binding.layoutLanguageWarn.layoutLanguageWarn.visibility = View.INVISIBLE
                    with(binding.chipGroup) {
                        val checkedChipList = checkedChipIds
                        checkedChipList.forEach {
                            val chip = findViewById<Chip>(it)
                            selectedChipIdList.add(chip.id)
                            selectedList.add(chip.text.toString())
                            otherListByKorean.add(ResourceUtil.getKoreanString(requireContext(), chip.tag.toString()))
                            idList.add(chip.tag.toString())
                        }
                    }

                    // todo : 선택된 증상 id
                    viewModel.setOtherSymptomList(selectedList)
                    viewModel.setOtherListByKorean(otherListByKorean)
                    viewModel.setOtherSymptomId(idList)
                    navController.navigate(R.id.selectAnesthesiaHistoryFragment)
                }
            }
            selectBoxYes.setOnClickListener {
                selected = true
                tvSymptomTitle.visibility = View.VISIBLE
                chipGroup.visibility = View.VISIBLE

                selectBoxYes.updateSelected(true)
                selectBoxNo.updateSelected(false)
            }

            selectBoxNo.setOnClickListener {
                selected = true
                binding.tvSymptomTitle.visibility = View.GONE
                binding.chipGroup.visibility = View.GONE
                selectBoxYes.updateSelected(false)
                selectBoxNo.updateSelected(true)
                selectedList = mutableListOf()
            }
        }
    }
}
