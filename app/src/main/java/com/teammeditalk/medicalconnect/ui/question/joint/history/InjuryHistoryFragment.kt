package com.teammeditalk.medicalconnect.ui.question.joint.history

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentInjuryHistoryBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InjuryHistoryFragment : BaseFragment<FragmentInjuryHistoryBinding>(FragmentInjuryHistoryBinding::inflate) {
    private val navController by lazy { findNavController() }
    private val viewModel: QuestionViewModel by activityViewModels()

    override fun onBindLayout() {
        super.onBindLayout()

        binding.btnBack.setOnClickListener { navController.popBackStack() }
        binding.btnNext.setOnClickListener {
            navController.navigate(R.id.selectJointWorseListFragment)
        }
    }
}
