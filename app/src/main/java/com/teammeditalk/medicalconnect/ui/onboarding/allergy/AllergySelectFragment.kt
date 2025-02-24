package com.teammeditalk.medicalconnect.ui.onboarding.allergy

import android.content.Intent
import androidx.fragment.app.activityViewModels
import com.google.android.material.chip.Chip
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
    private val allergyList = mutableListOf<String>()
    private val viewModel: OnBoardingViewModel by activityViewModels()

    override fun onBindLayout() {
        super.onBindLayout()

        binding.btnNext.setOnClickListener {
            with(binding.layoutFoodAllergy.chipGroup) {
                checkedChipIds.forEach {
                    val chip = findViewById<Chip>(it)
                    allergyList.add(chip.text.toString())
                }
            }

            with(binding.layoutDrugAllergy.chipGroup) {
                checkedChipIds.forEach {
                    val chip = findViewById<Chip>(it)
                    allergyList.add(chip.text.toString())
                }
            }
            with(binding.layoutOtherAllergy.chipGroup) {
                checkedChipIds.forEach {
                    val chip = findViewById<Chip>(it)
                    allergyList.add(chip.text.toString())
                }
            }

            viewModel.saveUserAllergy(allergyList)
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
