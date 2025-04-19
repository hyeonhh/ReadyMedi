package com.teammeditalk.medicalconnect.ui.question.woman

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentCycleRegularityBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CycleRegularityFragment :
    BaseFragment<FragmentCycleRegularityBinding>(
        FragmentCycleRegularityBinding::inflate,
    ) {
    private val viewModel: QuestionViewModel by activityViewModels()
    private val navController by lazy { findNavController() }
    private var isSelected: String = ""

    private var regularityByKorean = ""

    override fun onBindLayout() {
        super.onBindLayout()

        with(binding) {
            selectBoxNo.setOnClickListener {
                binding.warn.layoutLanguageWarn.visibility = View.INVISIBLE

                isSelected = selectBoxNo.getContent()
                selectBoxNo.updateSelected(true)
                selectBoxYes.updateSelected(false)
                regularityByKorean = it.tag.toString()
            }
            selectBoxYes.setOnClickListener {
                binding.warn.layoutLanguageWarn.visibility = View.INVISIBLE

                isSelected = selectBoxYes.getContent()
                selectBoxNo.updateSelected(false)
                selectBoxYes.updateSelected(true)
                regularityByKorean = it.tag.toString()
            }
            btnBack.setOnClickListener { navController.popBackStack() }
            btnNext.setOnClickListener {
                if (isSelected == "") {
                    binding.warn.layoutLanguageWarn.visibility = View.VISIBLE
                } else {
                    binding.warn.layoutLanguageWarn.visibility = View.INVISIBLE

                    viewModel.setRegularity(isSelected)
                    viewModel.setWomenMenstruationRegularity(regularityByKorean)
                    navController.navigate(R.id.womenOtherSymptomFragment)
                }
            }
        }
    }
}
