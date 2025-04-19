package com.teammeditalk.medicalconnect.ui.question.woman

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentPregnancyCheckBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PregnancyCheckFragment :
    BaseFragment<FragmentPregnancyCheckBinding>(
        FragmentPregnancyCheckBinding::inflate,
    ) {
    private val viewModel: QuestionViewModel by activityViewModels()
    private val navController by lazy { findNavController() }
    private var pregnancyAvailable: String = ""
    private var availableByKorean = ""

    override fun onBindLayout() {
        super.onBindLayout()

        with(binding) {
            selectBoxNo.setOnClickListener {
                binding.warn.layoutLanguageWarn.visibility = View.INVISIBLE
                selectBoxNo.updateSelected(true)
                selectBoxYes.updateSelected(false)
                selectBoxCurrent.updateSelected(false)
                pregnancyAvailable = selectBoxCurrent.getContent()
                availableByKorean = selectBoxCurrent.tag.toString()
            }

            selectBoxYes.setOnClickListener {
                binding.warn.layoutLanguageWarn.visibility = View.INVISIBLE

                selectBoxNo.updateSelected(false)
                selectBoxYes.updateSelected(true)
                selectBoxCurrent.updateSelected(false)
                pregnancyAvailable = selectBoxCurrent.getContent()
                availableByKorean = selectBoxCurrent.tag.toString()
            }

            selectBoxCurrent.setOnClickListener {
                binding.warn.layoutLanguageWarn.visibility = View.INVISIBLE

                selectBoxNo.updateSelected(false)
                selectBoxYes.updateSelected(false)
                selectBoxCurrent.updateSelected(true)
                pregnancyAvailable = selectBoxCurrent.getContent()
                availableByKorean = selectBoxCurrent.tag.toString()
            }
        }

        binding.btnBack.setOnClickListener { navController.popBackStack() }
        binding.btnNext.setOnClickListener {
            if (pregnancyAvailable == "") {
                binding.warn.layoutLanguageWarn.visibility = View.VISIBLE
            } else {
                viewModel.setPregnancyByKorean(availableByKorean)
                viewModel.setPregnancyAvailability(pregnancyAvailable)
                navController.navigate(R.id.lastPeriodFragment)
            }
        }
    }
}
