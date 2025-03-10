package com.teammeditalk.medicalconnect.ui.question.dental.history

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentAnesthesiaHistoryBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DentalSelectAnesthesiaHistoryFragment :
    BaseFragment<FragmentAnesthesiaHistoryBinding>(
        FragmentAnesthesiaHistoryBinding::inflate,
    ) {
    private val viewModel: QuestionViewModel by activityViewModels()
    private val navController by lazy { findNavController() }
    private var answer: Boolean = false

    override fun onBindLayout() {
        super.onBindLayout()

        with(binding) {
            selectBoxYes.setOnClickListener {
                answer = true
                selectBoxYes.updateSelected(true)
                selectBoxNo.updateSelected(false)
            }
            selectBoxNo.setOnClickListener {
                answer = false
                selectBoxYes.updateSelected(false)
                selectBoxNo.updateSelected(true)
            }
            btnNext.setOnClickListener {
                viewModel.setAnesthesiaHistory(answer)
                navController.navigate(R.id.dentalSelectSideEffectFragment)
            }
            btnBack.setOnClickListener {
                navController.popBackStack()
            }
        }
    }
}
