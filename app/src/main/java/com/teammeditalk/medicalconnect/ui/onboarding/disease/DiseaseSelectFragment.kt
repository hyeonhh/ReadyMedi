package com.teammeditalk.medicalconnect.ui.onboarding.disease

import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentDiseaseSelectBinding
import com.teammeditalk.medicalconnect.ui.onboarding.OnBoardingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiseaseSelectFragment :
    BaseFragment<FragmentDiseaseSelectBinding>(
        FragmentDiseaseSelectBinding::inflate,
    ) {
    private val navController by lazy { findNavController() }
    private val viewModel: OnBoardingViewModel by activityViewModels()

    private fun onChipClick(view: View) {
        view as Chip
        view.isSelected = !view.isSelected
        if (view.isSelected) {
            view.setChipStrokeColorResource(R.color.blue50)
            view.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue50))
        } else {
            view.setChipStrokeColorResource(R.color.gray10)
            //  view.setTextColor(resources.getColor(R.color.gray10, null))
            view.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray95))
        }
    }

    override fun onBindLayout() {
        super.onBindLayout()
        binding.btnNext.setOnClickListener {
            navController.navigate(R.id.familyDiseaseSelectFragment)
        }

        with(binding.layoutCancer.chipGroup) {
            forEach { child ->
                (child as Chip).let {
                    it.setOnClickListener { onChipClick(it) }
                }
            }
        }
        with(binding.layoutOtherDisease.chipGroup) {
            forEach { child ->
                (child as Chip).let {
                    it.setOnClickListener { onChipClick(it) }
                }
            }
        }
        with(binding.layoutSurgery.chipGroup) {
            forEach { child ->
                (child as Chip).let {
                    it.setOnClickListener { onChipClick(it) }
                }
            }
        }
    }
}
