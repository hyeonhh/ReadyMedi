package com.teammeditalk.medicalconnect.ui.question.inner.other

import android.view.View
import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentGeneralOtherSymptomBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GeneralOtherSymptomFragment :
    BaseFragment<FragmentGeneralOtherSymptomBinding>(
        FragmentGeneralOtherSymptomBinding::inflate,
    ) {
    private val viewModel: QuestionViewModel by activityViewModels()
    private val otherSymptomList = mutableListOf<String>()
    private val navController by lazy { findNavController() }

    override fun onBindLayout() {
        super.onBindLayout()

        with(binding) {
            selectBoxYes.setOnClickListener {
                selectBoxYes.updateSelected(true)
                selectBoxNo.updateSelected(false)
                tvSymptomTitle.visibility = View.VISIBLE
                chipGroupSymptoms.visibility = View.VISIBLE
            }
            selectBoxNo.setOnClickListener {
                selectBoxYes.updateSelected(false)
                selectBoxNo.updateSelected(true)
                tvSymptomTitle.visibility = View.GONE
                chipGroupSymptoms.visibility = View.GONE
            }
        }
        with(binding.chipGroupSymptoms) {
            for (child in children) {
                child.setOnClickListener {
                    if (it.isSelected) {
                        otherSymptomList.add((it as Chip).text.toString())
                    } else {
                        otherSymptomList.remove((it as Chip).text.toString())
                    }
                }
            }
        }
        binding.btnBack.setOnClickListener { navController.popBackStack() }
        binding.btnNext.setOnClickListener {
            viewModel.setOtherSymptomList(otherSymptomList)
            val action = GeneralOtherSymptomFragmentDirections.actionGeneralOtherSymptomFragmentToAdditionalInputFragment("일반")
            navController.navigate(action)
        }
    }
}
