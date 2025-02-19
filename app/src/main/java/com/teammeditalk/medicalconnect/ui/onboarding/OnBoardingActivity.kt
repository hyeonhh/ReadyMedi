package com.teammeditalk.medicalconnect.ui.onboarding

import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseActivity
import com.teammeditalk.medicalconnect.databinding.ActivityOnboardingBinding

class OnBoardingActivity :
    BaseActivity<ActivityOnboardingBinding>(
        ActivityOnboardingBinding::inflate,
    ) {
    private val onBoardingViewModel: OnBoardingViewModel by viewModels()

    private val navController by lazy {
        val navHostFragment =
            supportFragmentManager
                .findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navHostFragment.navController
    }

    override fun onBindLayout() {
        super.onBindLayout()
    }
}
