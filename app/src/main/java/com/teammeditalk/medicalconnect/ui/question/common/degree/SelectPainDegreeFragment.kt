package com.teammeditalk.medicalconnect.ui.question.common.degree

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentSelectPainDegreeBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import dagger.hilt.android.AndroidEntryPoint

// 4. 통증의 강도
@AndroidEntryPoint
class SelectPainDegreeFragment :
    BaseFragment<FragmentSelectPainDegreeBinding>(
        FragmentSelectPainDegreeBinding::inflate,
    ) {
    private val navController by lazy { findNavController() }
    private var degree: Float = 0.0F
    private val viewModel: QuestionViewModel by activityViewModels()

    override fun onBindLayout() {
        super.onBindLayout()

        binding.slider.addOnChangeListener { _, value, _ ->
            degree = value
        }

        binding.btnBack.setOnClickListener { navController.popBackStack() }
        binding.btnNext.setOnClickListener {
            viewModel.selectDegree(degree)
            navController.navigate(R.id.selectDurationFragment)
        }
    }
}
