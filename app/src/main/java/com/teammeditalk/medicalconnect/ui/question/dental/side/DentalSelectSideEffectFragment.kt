package com.teammeditalk.medicalconnect.ui.question.dental.side

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentSideEffectBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DentalSelectSideEffectFragment :
    BaseFragment<FragmentSideEffectBinding>(
        FragmentSideEffectBinding::inflate,
    ) {
    private val viewModel: QuestionViewModel by activityViewModels()
    private val navController by lazy { findNavController() }

    override fun onBindLayout() {
        super.onBindLayout()

        binding.btnNext.setOnClickListener {
            val action = DentalSelectSideEffectFragmentDirections.actionDentalSelectSideEffectFragmentToAdditionalInputFragment("치과")
            navController.navigate(action)
        }
    }
}
