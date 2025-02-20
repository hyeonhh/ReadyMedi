package com.teammeditalk.medicalconnect.ui.question.common

import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentSelectPainDegreeBinding
import dagger.hilt.android.AndroidEntryPoint

// 4. 통증의 강도
@AndroidEntryPoint
class SelectPainDegreeFragment :
    BaseFragment<FragmentSelectPainDegreeBinding>(
        FragmentSelectPainDegreeBinding::inflate,
    ) {
    private val navController by lazy { findNavController() }

    override fun onBindLayout() {
        super.onBindLayout()

        binding.btnNext.setOnClickListener {
            navController.navigate(R.id.selectDurationFragment)
        }
    }
}
