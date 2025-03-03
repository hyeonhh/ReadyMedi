package com.teammeditalk.medicalconnect.ui.my.symptom

import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentMySymptomHistoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MySymptomHistoryFragment :
    BaseFragment<FragmentMySymptomHistoryBinding>(
        FragmentMySymptomHistoryBinding::inflate,
    ) {
    private val navController by lazy { findNavController() }

    override fun onBindLayout() {
        super.onBindLayout()

        binding.emptyView.btnGoToSelectSymptom.setOnClickListener {
            navController.navigate(R.id.question_nav)
        }
        binding.btnBack.setOnClickListener { navController.popBackStack() }
    }
}
