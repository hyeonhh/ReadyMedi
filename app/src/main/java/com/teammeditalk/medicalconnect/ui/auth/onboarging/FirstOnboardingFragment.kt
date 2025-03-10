package com.teammeditalk.medicalconnect.ui.auth.onboarging

import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentOnboarding1Binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstOnboardingFragment :
    BaseFragment<FragmentOnboarding1Binding>(
        FragmentOnboarding1Binding::inflate,
    ) {
    override fun onBindLayout() {
        super.onBindLayout()

        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.secondOnboardingFragment)
        }
    }
}
