package com.teammeditalk.medicalconnect.ui.question.common.symptom

import android.view.View
import androidx.core.view.children
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
            for (child in layoutSymptomCategory.children) {
                child.setOnClickListener {
                    it.isSelected = !it.isSelected
                }
            }
            btnSymptom.setOnClickListener {
                it.isSelected = !it.isSelected
                layoutSymptomCategory.visibility = if (it.isSelected) View.VISIBLE else View.GONE
            }
        }

        with(binding.layoutDigestive) {
            btnSymptom.setOnClickListener {
                it.isSelected = !it.isSelected
                layoutDigestive.visibility = if (it.isSelected) View.VISIBLE else View.GONE
            }
        }

        with(binding.layoutFatigue) {
            btnSymptom.setOnClickListener {
                it.isSelected = !it.isSelected
                layoutFatigue.visibility = if (it.isSelected) View.VISIBLE else View.GONE
            }
        }

        with(binding.layoutAllergy) {
            btnSymptom.setOnClickListener {
                it.isSelected = !it.isSelected
                layoutAllergy.visibility = if (it.isSelected) View.VISIBLE else View.GONE
            }
        }

        with(binding.layoutChronic) {
            btnSymptom.setOnClickListener {
                it.isSelected = !it.isSelected
                layoutChronic.visibility = if (it.isSelected) View.VISIBLE else View.GONE
            }
        }

        with(binding.layoutJoint) {
            btnSymptom.setOnClickListener {
                it.isSelected = !it.isSelected
                layoutJoint.visibility = if (it.isSelected) View.VISIBLE else View.GONE
            }
        }
        with(binding.layoutInjury) {
            btnSymptom.setOnClickListener {
                it.isSelected = !it.isSelected
                layoutInjury.visibility = if (it.isSelected) View.VISIBLE else View.GONE
            }
        }

        with(binding.layoutDental) {
            btnSymptom.setOnClickListener {
                it.isSelected = !it.isSelected
                layoutDental.visibility = if (it.isSelected) View.VISIBLE else View.GONE
            }
        }
        with(binding.layoutBreast) {
            btnSymptom.setOnClickListener {
                it.isSelected = !it.isSelected
                layoutBreast.visibility = if (it.isSelected) View.VISIBLE else View.GONE
            }
        }
        with(binding.layoutWomen) {
            btnSymptom.setOnClickListener {
                it.isSelected = !it.isSelected
                layoutWomen.visibility = if (it.isSelected) View.VISIBLE else View.GONE
            }
        }
    }
}
