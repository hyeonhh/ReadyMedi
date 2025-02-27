package com.teammeditalk.medicalconnect.ui.question.common.input

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentAdditionalInputBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdditionalInputFragment :
    BaseFragment<FragmentAdditionalInputBinding>(
        FragmentAdditionalInputBinding::inflate,
    ) {
    private val navController by lazy { findNavController() }
    private val viewModel: QuestionViewModel by activityViewModels()

    override fun onBindLayout() {
        super.onBindLayout()

        binding.btnBack.setOnClickListener { navController.popBackStack() }
        binding.btnComplete.setOnClickListener {
            viewModel.setAdditionalInput(binding.editText.getContent())
            viewModel.processSymptomQuestion()
            navController.navigate(R.id.loadingFragment)
        }
    }
}
