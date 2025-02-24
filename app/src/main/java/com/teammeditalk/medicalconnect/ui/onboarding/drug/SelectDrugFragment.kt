package com.teammeditalk.medicalconnect.ui.onboarding.drug

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentSelectDrugBinding
import com.teammeditalk.medicalconnect.ui.onboarding.OnBoardingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectDrugFragment :
    BaseFragment<FragmentSelectDrugBinding>(
        FragmentSelectDrugBinding::inflate,
    ) {
    private val drugList = mutableListOf<String>()
    private val viewModel: OnBoardingViewModel by activityViewModels()

    override fun onBindLayout() {
        super.onBindLayout()

        binding.btnNext.setOnClickListener {
            with(binding.chipGroup) {
                checkedChipIds.forEach {
                    val chip = findViewById<Chip>(it)
                    drugList.add(chip.text.toString())
                }
            }
            viewModel.saveUserDrug(drugList)
            findNavController().navigate(R.id.allergySelectFragment)
        }
    }
}
