package com.teammeditalk.medicalconnect.ui.question.dental.fragment.other

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentSelectOtherDentalSymptomBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

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

    override fun onBindLayout() {
        super.onBindLayout()

        with(binding) {
            btnBack.setOnClickListener { navController.popBackStack() }
            btnNext.setOnClickListener {
                if (!selected) {
                    binding.layoutLanguageWarn.layoutLanguageWarn.visibility = View.VISIBLE
                } else {
                    with(binding.chipGroup) {
                        val checkedChipList = checkedChipIds
                        checkedChipList.forEach {
                            val chip = findViewById<Chip>(it)
                            selectedChipIdList.add(chip.id)
                            selectedList.add(chip.text.toString())
                        }
                        Timber.d("선택된 치과 동반 증상 id :$selected")
                    }

                    viewModel.setOtherSymptomList(selectedList)
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
