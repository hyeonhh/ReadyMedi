package com.teammeditalk.medicalconnect.ui.onboarding

import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentLanguageSelectBinding

class LanguageSelectFragment :
    BaseFragment<FragmentLanguageSelectBinding>(
        FragmentLanguageSelectBinding::inflate,
    ) {
    private val navController by lazy { findNavController() }

    override fun onBindLayout() {
        super.onBindLayout()

        binding.materialButton.setOnClickListener {
            navController.navigate(R.id.diseaseSelectFragment)
        }
    }
}
