package com.teammeditalk.medicalconnect.ui.auth.onboarging

import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentOnboarding2Binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecondOnboardingFragment :
    BaseFragment<FragmentOnboarding2Binding>(
        FragmentOnboarding2Binding::inflate,
    ) {
    override fun onBindLayout() {
        super.onBindLayout()

        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.thirdOnBoardingFragment)
        }
    }
}
