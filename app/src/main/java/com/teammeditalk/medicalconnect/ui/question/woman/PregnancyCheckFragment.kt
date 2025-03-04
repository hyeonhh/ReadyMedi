package com.teammeditalk.medicalconnect.ui.question.woman

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

    override fun onBindLayout() {
        super.onBindLayout()

        with(binding) {
            selectBoxNo.setOnClickListener {
                selectBoxNo.updateSelected(true)
                selectBoxYes.updateSelected(false)
                selectBoxCurrent.updateSelected(false)
                pregnancyAvailable = selectBoxCurrent.getContent()
            }

            selectBoxYes.setOnClickListener {
                selectBoxNo.updateSelected(false)
                selectBoxYes.updateSelected(true)
                selectBoxCurrent.updateSelected(false)
                pregnancyAvailable = selectBoxCurrent.getContent()
            }

            selectBoxCurrent.setOnClickListener {
                selectBoxNo.updateSelected(false)
                selectBoxYes.updateSelected(false)
                selectBoxCurrent.updateSelected(true)
                pregnancyAvailable = selectBoxCurrent.getContent()
            }
        }

        binding.btnBack.setOnClickListener { navController.popBackStack() }
        binding.btnNext.setOnClickListener {
            viewModel.setPregnancyAvailability(pregnancyAvailable)
            navController.navigate(R.id.lastPeriodFragment)
        }
    }
}
