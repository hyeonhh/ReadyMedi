package com.teammeditalk.medicalconnect.ui.question.common.effect

import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentSelectSympmtomEffectBinding
import dagger.hilt.android.AndroidEntryPoint

// 통증을 완화 또는 악화 시키는 요인
@AndroidEntryPoint
class SelectSymptomWorseFragment :
    BaseFragment<FragmentSelectSympmtomEffectBinding>(
        FragmentSelectSympmtomEffectBinding::inflate,
    ) {
    private val navController by lazy { findNavController() }

    override fun onBindLayout() {
        super.onBindLayout()

        binding.btnNext.setOnClickListener {
            navController.navigate(R.id.selectOtherSymptomFragment)
        }
    }
}
