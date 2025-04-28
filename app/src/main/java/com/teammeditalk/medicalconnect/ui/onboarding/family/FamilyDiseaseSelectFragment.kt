package com.teammeditalk.medicalconnect.ui.onboarding.family

import androidx.core.view.forEach
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
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
    private val familyDiseaseList = mutableListOf<String>()

    override fun onBindLayout() {
        super.onBindLayout()

        binding.btnBack.setOnClickListener { navController.popBackStack() }

        binding.btnNext.setOnClickListener {
            with(binding.chipGroup) {
                checkedChipIds.forEach {
                    val chip = findViewById<Chip>(it)
                    familyDiseaseList.add(chip.tag.toString())
                }
            }

            viewModel.saveUserFamilyDisease(familyDiseaseList)
            navController.navigate(R.id.selectDrugFragment)
        }
    }
}
