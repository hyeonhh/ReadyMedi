package com.teammeditalk.medicalconnect.ui.history.health

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentMyHealthInfoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyHealthInfoFragment :
    BaseFragment<FragmentMyHealthInfoBinding>(
        FragmentMyHealthInfoBinding::inflate,
    ) {
    private val navController by lazy { findNavController() }
    private val viewModel: MyHealthInfoViewModel by viewModels()

    override fun onBindLayout() {
        super.onBindLayout()

        with(binding) {
            btnBack.setOnClickListener { navController.popBackStack() }

            layoutUserDisease.layout.setOnClickListener {
                navController.navigate(R.id.editDiseaseFragment)
            }

            layoutFamilyDisease.layout.setOnClickListener {
                navController.navigate(R.id.editFamilyDiseaseFragment)
            }
            layoutAllergy.layout.setOnClickListener {
                navController.navigate(R.id.editAllergyFragment)
            }

            layoutMyDrug.layout.setOnClickListener {
                navController.navigate(R.id.editDrugFragment)
            }

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.userHealthInfo.collectLatest {
                        layoutUserDisease.tvDisease.text =
                            if (it.diseaseList.isEmpty()) {
                                getString(R.string.not_applicable)
                            } else if (it.diseaseList.size > 1) {
                                "${it.diseaseList[0]} 외 ${it.diseaseList.size - 1}"
                            } else {
                                it.diseaseList[0]
                            }

                        layoutFamilyDisease.tvFamilyDisease.text =
                            if (it.familyDiseaseList.isEmpty()) {
                                getString(R.string.not_applicable)
                            } else if (it.familyDiseaseList.size > 1) {
                                "${it.familyDiseaseList[0]} 외 ${it.familyDiseaseList.size - 1}"
                            } else {
                                it.familyDiseaseList[0]
                            }

                        layoutMyDrug.tvDrug.text =
                            if (it.drugList.isEmpty()) {
                                getString(R.string.not_applicable)
                            } else if (it.drugList.size > 1) {
                                "${it.drugList[0]} 외 ${it.drugList.size - 1}"
                            } else {
                                it.drugList[0]
                            }
                        layoutAllergy.tvAllergy.text =
                            if (it.allergyList.isEmpty()) {
                                getString(R.string.not_applicable)
                            } else if (it.allergyList.size > 1) {
                                "${it.allergyList[0]} 외 ${it.allergyList.size - 1}"
                            } else {
                                it.allergyList[0]
                            }
                    }
                }
            }
        }
    }
}
