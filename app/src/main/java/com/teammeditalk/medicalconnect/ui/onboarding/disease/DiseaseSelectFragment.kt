package com.teammeditalk.medicalconnect.ui.onboarding.disease

import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentDiseaseSelectBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiseaseSelectFragment :
    BaseFragment<FragmentDiseaseSelectBinding>(
        FragmentDiseaseSelectBinding::inflate,
    ) {
    private val navController by lazy { findNavController() }

    override fun onBindLayout() {
        super.onBindLayout()
        binding.btnNext.setOnClickListener {
            navController.navigate(R.id.familyDiseaseSelectFragment)
        }
    }
}
