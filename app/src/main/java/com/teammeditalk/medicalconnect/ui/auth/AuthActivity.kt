package com.teammeditalk.medicalconnect.ui.auth

import android.content.Intent
import com.teammeditalk.medicalconnect.base.BaseActivity
import com.teammeditalk.medicalconnect.databinding.ActivityAuthBinding
import com.teammeditalk.medicalconnect.ui.onboarding.OnBoardingActivity

class AuthActivity :
    BaseActivity<ActivityAuthBinding>(
        ActivityAuthBinding::inflate,
    ) {
    override fun onBindLayout() {
        super.onBindLayout()

        binding.btnGoogleLogin.setOnClickListener {
            val intent = Intent(this, OnBoardingActivity::class.java)
            startActivity(intent)
        }
    }
}
