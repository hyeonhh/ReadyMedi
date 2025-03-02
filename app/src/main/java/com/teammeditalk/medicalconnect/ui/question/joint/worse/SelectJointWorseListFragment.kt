package com.teammeditalk.medicalconnect.ui.question.dental.fragment.worse

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentSelectJointWorseListBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectJointWorseListFragment :
    BaseFragment<FragmentSelectJointWorseListBinding>(
        FragmentSelectJointWorseListBinding::inflate,
    ) {
    private val navController by lazy { findNavController() }
    private val viewModel: QuestionViewModel by activityViewModels()

    override fun onBindLayout() {
        super.onBindLayout()

        binding.layoutConstraint.apply {
        }
        binding.btnNext.setOnClickListener {
            navController.navigate(R.id.selectJointOtherSymptomFragment)
        }
    }
}
