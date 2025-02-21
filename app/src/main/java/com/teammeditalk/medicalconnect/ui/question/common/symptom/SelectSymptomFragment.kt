package com.teammeditalk.medicalconnect.ui.question.common.symptom

import android.view.View
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentSelectSymptomBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectSymptomFragment :
    BaseFragment<FragmentSelectSymptomBinding>(
        FragmentSelectSymptomBinding::inflate,
    ) {
    private val navController by lazy { findNavController() }

    private fun onChipClick(view: View) {
        view.isSelected = !view.isSelected
    }

    override fun onBindLayout() {
        super.onBindLayout()

        binding.btnNext.setOnClickListener {
            navController.navigate(R.id.selectSymptomRegionFragment)
        }

        with(binding.layoutRespiratory) {
            btnSymptom.setOnClickListener {
                it.isSelected = !it.isSelected
                chipGroup.visibility = if (it.isSelected) View.VISIBLE else View.GONE
            }
            chip1.setOnClickListener { onChipClick(it) }
            chip2.setOnClickListener { onChipClick(it) }
            chip3.setOnClickListener { onChipClick(it) }
            chip4.setOnClickListener { onChipClick(it) }
            chip5.setOnClickListener { onChipClick(it) }
        }

        with(binding.layoutDigestive) {
            btnSymptom.setOnClickListener {
                it.isSelected = !it.isSelected
                chipGroup.visibility = if (it.isSelected) View.VISIBLE else View.GONE

                chip1.setOnClickListener { onChipClick(it) }
                chip2.setOnClickListener { onChipClick(it) }
                chip3.setOnClickListener { onChipClick(it) }
                chip4.setOnClickListener { onChipClick(it) }
                chip5.setOnClickListener { onChipClick(it) }
            }
        }

        with(binding.layoutFatigue) {
            btnSymptom.setOnClickListener {
                it.isSelected = !it.isSelected
                chipGroup.visibility = if (it.isSelected) View.VISIBLE else View.GONE
            }
            chip1.setOnClickListener { onChipClick(it) }
            chip2.setOnClickListener { onChipClick(it) }
            chip3.setOnClickListener { onChipClick(it) }
            chip4.setOnClickListener { onChipClick(it) }
            chip5.setOnClickListener { onChipClick(it) }
        }

        with(binding.layoutAllergy) {
            btnSymptom.setOnClickListener {
                it.isSelected = !it.isSelected
                chipGroup.visibility = if (it.isSelected) View.VISIBLE else View.GONE
            }

            chip1.setOnClickListener { onChipClick(it) }
            chip2.setOnClickListener { onChipClick(it) }
            chip3.setOnClickListener { onChipClick(it) }
            chip4.setOnClickListener { onChipClick(it) }
        }

        with(binding.layoutChronic) {
            btnSymptom.setOnClickListener {
                it.isSelected = !it.isSelected
                chipGroup.visibility = if (it.isSelected) View.VISIBLE else View.GONE

                chip1.setOnClickListener { onChipClick(it) }
                chip2.setOnClickListener { onChipClick(it) }
                chip3.setOnClickListener { onChipClick(it) }
            }
        }

        with(binding.layoutJoint) {
            btnSymptom.setOnClickListener {
                it.isSelected = !it.isSelected
                chipGroup.visibility = if (it.isSelected) View.VISIBLE else View.GONE
            }
            chip1.setOnClickListener { onChipClick(it) }
            chip2.setOnClickListener { onChipClick(it) }
            chip3.setOnClickListener { onChipClick(it) }
            chip4.setOnClickListener { onChipClick(it) }
            chip5.setOnClickListener { onChipClick(it) }
        }
        with(binding.layoutInjury) {
            btnSymptom.setOnClickListener {
                it.isSelected = !it.isSelected
                chipGroup.visibility = if (it.isSelected) View.VISIBLE else View.GONE
            }
            chip1.setOnClickListener { onChipClick(it) }
            chip2.setOnClickListener { onChipClick(it) }
            chip3.setOnClickListener { onChipClick(it) }
        }

        with(binding.layoutDental) {
            btnSymptom.setOnClickListener {
                it.isSelected = !it.isSelected
                chipGroup.visibility = if (it.isSelected) View.VISIBLE else View.GONE
            }
            chip1.setOnClickListener { onChipClick(it) }
            chip2.setOnClickListener { onChipClick(it) }
            chip3.setOnClickListener { onChipClick(it) }
            chip4.setOnClickListener { onChipClick(it) }
            chip5.setOnClickListener { onChipClick(it) }
            chip6.setOnClickListener { onChipClick(it) }
            chip7.setOnClickListener { onChipClick(it) }
            chip8.setOnClickListener { onChipClick(it) }
            chip9.setOnClickListener { onChipClick(it) }
        }
        with(binding.layoutBreast) {
            btnSymptom.setOnClickListener {
                it.isSelected = !it.isSelected
                chipGroup.visibility = if (it.isSelected) View.VISIBLE else View.GONE
            }
            chip1.setOnClickListener { onChipClick(it) }
            chip2.setOnClickListener { onChipClick(it) }
            chip3.setOnClickListener { onChipClick(it) }
        }
        with(binding.layoutWomen) {
            btnSymptom.setOnClickListener {
                it.isSelected = !it.isSelected
                chipGroup.visibility = if (it.isSelected) View.VISIBLE else View.GONE
            }
            chip1.setOnClickListener { onChipClick(it) }
            chip2.setOnClickListener { onChipClick(it) }
            chip3.setOnClickListener { onChipClick(it) }
            chip4.setOnClickListener { onChipClick(it) }
            chip5.setOnClickListener { onChipClick(it) }
        }
    }
}
