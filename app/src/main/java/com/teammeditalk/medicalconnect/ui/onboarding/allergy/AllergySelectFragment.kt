package com.teammeditalk.medicalconnect.ui.onboarding.allergy

import android.content.Intent
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.MainActivity
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentAllergySelectBinding
import com.teammeditalk.medicalconnect.ui.onboarding.OnBoardingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllergySelectFragment :
    BaseFragment<FragmentAllergySelectBinding>(
        FragmentAllergySelectBinding::inflate,
    ) {
    private val navController by lazy { findNavController() }
    private val viewModel: OnBoardingViewModel by activityViewModels()

    private fun onChipClick(view: View) {
        view.isSelected = !view.isSelected
    }

    override fun onBindLayout() {
        super.onBindLayout()

        with(binding.layoutFoodAllergy) {
            chip1.setOnClickListener { onChipClick(it) }
            chip2.setOnClickListener { onChipClick(it) }
            chip3.setOnClickListener { onChipClick(it) }
            chip4.setOnClickListener { onChipClick(it) }
            chip5.setOnClickListener { onChipClick(it) }
            chip6.setOnClickListener { onChipClick(it) }
        }

        with(binding.layoutDrugAllergy) {
            chip1.setOnClickListener { onChipClick(it) }
            chip2.setOnClickListener { onChipClick(it) }
            chip3.setOnClickListener { onChipClick(it) }
            chip4.setOnClickListener { onChipClick(it) }
            chip5.setOnClickListener { onChipClick(it) }
            chip6.setOnClickListener { onChipClick(it) }
        }
        with(binding.layoutOtherAllergy) {
            chip1.setOnClickListener { onChipClick(it) }
            chip2.setOnClickListener { onChipClick(it) }
            chip3.setOnClickListener { onChipClick(it) }
            chip4.setOnClickListener { onChipClick(it) }
        }

        binding.btnNext.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
