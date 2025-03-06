package com.teammeditalk.medicalconnect.ui.onboarding.disease

import androidx.core.view.children
import androidx.core.view.forEach
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentEditDiseaseBinding
import com.teammeditalk.medicalconnect.ui.edit.EditDiseaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditDiseaseFragment :
    BaseFragment<FragmentEditDiseaseBinding>(
        FragmentEditDiseaseBinding::inflate,
    ) {
    private val navController by lazy { findNavController() }
    private val viewModel: EditDiseaseViewModel by viewModels()
    private val diseaseList: MutableList<String> = mutableListOf()

    private fun updateChipsFromDiseaseList(list: List<String>) {
        // 모든 ChipGroup을 순회하며 질병 목록에 따라 체크 상태 업데이트
        listOf(
            binding.layoutCancer.chipGroup,
            binding.layoutOtherDisease.chipGroup,
            binding.layoutSurgery.chipGroup,
        ).forEach { chipGroup ->
            chipGroup.children.forEach { child ->
                val chip = child as? Chip ?: return@forEach
                // 상태 업데이트만 하고 리스너는 건드리지 않음
                chip.isChecked = list.contains(chip.text.toString())
            }
        }
    }

    override fun onBindLayout() {
        super.onBindLayout()
        // 이제 Flow 수집에서는 클릭 리스너 설정 없이 상태만 업데이트
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.diseaseList.collect { diseases ->
                    updateChipsFromDiseaseList(diseases)
                }
            }
        }

        binding.btnBack.setOnClickListener { navController.popBackStack() }
        binding.btnNext.setOnClickListener {
            with(binding.layoutCancer.chipGroup) {
                val checkedChipList = checkedChipIds
                checkedChipList.forEach {
                    val chip = findViewById<Chip>(it)
                    diseaseList.add(chip.text.toString())
                }
            }
            with(binding.layoutOtherDisease.chipGroup) {
                val checkedChipList = checkedChipIds
                checkedChipList.forEach {
                    val chip = findViewById<Chip>(it)
                    diseaseList.add(chip.text.toString())
                }
            }
            with(binding.layoutSurgery.chipGroup) {
                val checkedChipList = checkedChipIds
                checkedChipList.forEach {
                    val chip = findViewById<Chip>(it)
                    diseaseList.add(chip.text.toString())
                }
            }
            viewModel.editDisease(diseaseList)
            navController.navigate(R.id.myHealthInfoFragment)
        }
    }
}
