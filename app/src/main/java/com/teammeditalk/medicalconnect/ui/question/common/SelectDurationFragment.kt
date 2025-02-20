package com.teammeditalk.medicalconnect.ui.question.common

import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentSelectDurationBinding
import dagger.hilt.android.AndroidEntryPoint

// 5. 통증의 지속 시간
@AndroidEntryPoint
class SelectDurationFragment :
    BaseFragment<FragmentSelectDurationBinding>(
        FragmentSelectDurationBinding::inflate,
    ) {
    private val navController by lazy { findNavController() }

    override fun onBindLayout() {
        super.onBindLayout()

        binding.btnNext.setOnClickListener {
            navController.navigate(R.id.selectSymptomEffectFragment)
        }
    }
}
