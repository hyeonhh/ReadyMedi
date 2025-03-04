package com.teammeditalk.medicalconnect.ui.question.woman

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

    override fun onBindLayout() {
        super.onBindLayout()

        with(binding) {
            selectBoxNo.setOnClickListener {
                isSelected = selectBoxNo.getContent()
                selectBoxNo.updateSelected(true)
                selectBoxYes.updateSelected(false)
            }
            selectBoxYes.setOnClickListener {
                isSelected = selectBoxYes.getContent()
                selectBoxNo.updateSelected(false)
                selectBoxYes.updateSelected(true)
            }
            btnBack.setOnClickListener { navController.popBackStack() }
            btnNext.setOnClickListener {
                viewModel.setRegularity(isSelected)
                navController.navigate(R.id.womenOtherSymptomFragment)
            }
        }
    }
}
