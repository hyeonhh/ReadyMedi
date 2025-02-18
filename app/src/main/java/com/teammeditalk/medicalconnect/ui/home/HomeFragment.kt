package com.teammeditalk.medicalconnect.ui.home

import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentHomeBinding

class HomeFragment :
    BaseFragment<FragmentHomeBinding>(
        FragmentHomeBinding::inflate,
    ) {
    private val navController by lazy { findNavController() }

    override fun onBindLayout() {
        super.onBindLayout()

        binding.btnTranslate.setOnClickListener {
            navController.navigate(R.id.symptomResultTranslateFragment)
        }
    }
}
