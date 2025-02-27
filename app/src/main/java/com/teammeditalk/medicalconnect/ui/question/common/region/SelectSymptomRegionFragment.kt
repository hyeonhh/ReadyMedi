package com.teammeditalk.medicalconnect.ui.question.common.region

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentSelectSymptomRegionBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import dagger.hilt.android.AndroidEntryPoint

// 1. 증상 부위
@AndroidEntryPoint
class SelectSymptomRegionFragment :
    BaseFragment<FragmentSelectSymptomRegionBinding>(
        FragmentSelectSymptomRegionBinding::inflate,
    ) {
    private val viewModel: QuestionViewModel by activityViewModels()
    private val navController by lazy { findNavController() }

    override fun onBindLayout() {
        super.onBindLayout()

        binding.btnBack.setOnClickListener {
            navController.popBackStack()
        }
        binding.btnNext.setOnClickListener {
            // todo : 얘는 어떻게 저장할지 고민해보기 !
            navController.navigate(R.id.selectSymptomStartFragment)
        }
    }
}
