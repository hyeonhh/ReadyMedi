package com.teammeditalk.medicalconnect.ui.translate

import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentSypmtomResultTranslateBinding

class SymptomResultTranslateFragment : BaseFragment<FragmentSypmtomResultTranslateBinding>(
    FragmentSypmtomResultTranslateBinding::inflate
) {
    override fun onBindLayout() {
        super.onBindLayout()

        binding.tvTest
    }
}