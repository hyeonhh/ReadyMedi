package com.teammeditalk.medicalconnect.ui.history.health

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
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

            lifecycleScope.launch {
                viewModel.userHealthInfo.collectLatest {
                    layoutUserDisease.tvDisease.text =
                        if (it.diseaseList.isEmpty()) {
                            "해당 없음"
                        } else if (it.diseaseList.size >
                            1
                        ) {
                            "${it.diseaseList[0]}외 ${it.diseaseList.size - 1}"
                        } else {
                            it.diseaseList[0]
                        }

                    layoutFamilyDisease.tvFamilyDisease.text =
                        if (it.familyDiseaseList.isEmpty()) "해당 없음" else it.familyDiseaseList.joinToString(",", "", "")
                    layoutMyDrug.tvDrug.text = if (it.drugList.isEmpty()) "해당 없음" else it.drugList.joinToString(",", "", "")
                    layoutAllergy.tvAllergy.text = if (it.allergyList.isEmpty())"해당 없음" else it.allergyList.joinToString(",", "", "")
                }
            }
        }
    }
}
