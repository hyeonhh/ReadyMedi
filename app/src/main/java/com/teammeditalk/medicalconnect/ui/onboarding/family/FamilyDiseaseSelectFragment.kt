package com.teammeditalk.medicalconnect.ui.onboarding.family

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentFamilyDiseaseSelectBinding
import com.teammeditalk.medicalconnect.ui.onboarding.OnBoardingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FamilyDiseaseSelectFragment :
    BaseFragment<FragmentFamilyDiseaseSelectBinding>(
        FragmentFamilyDiseaseSelectBinding::inflate,
    ) {
    private val navController by lazy { findNavController() }
    private val viewModel: OnBoardingViewModel by activityViewModels()

    override fun onBindLayout() {
        super.onBindLayout()

        binding.btnNext.setOnClickListener {
            navController.navigate(R.id.selectDrugFragment)
        }
    }
}
