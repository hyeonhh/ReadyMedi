package com.teammeditalk.medicalconnect.ui.question.woman

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentLastPeriodBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LastPeriodFragment :
    BaseFragment<FragmentLastPeriodBinding>(
        FragmentLastPeriodBinding::inflate,
    ) {
    private val viewModel: QuestionViewModel by activityViewModels()
    private val navController by lazy { findNavController() }

    override fun onBindLayout() {
        super.onBindLayout()

        with(binding) {
            btnBack.setOnClickListener { navController.popBackStack() }
            btnNext.setOnClickListener {
                navController.navigate(R.id.cycleRegularityFragment)
            }
        }
    }
}
