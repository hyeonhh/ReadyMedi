package com.teammeditalk.medicalconnect.ui.edit.allergy

import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.EditFragmentAllergyBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditAllergyFragment :
    BaseFragment<EditFragmentAllergyBinding>(
        EditFragmentAllergyBinding::inflate,
    ) {
    private val navController by lazy { findNavController() }
    private val viewModel: EditAllergyViewModel by viewModels()
    private var allergyList: MutableList<String> = mutableListOf()

    private fun updateChipsFromDiseaseList(list: List<String>) {
        // 모든 ChipGroup을 순회하며 질병 목록에 따라 체크 상태 업데이트
        listOf(
            binding.layoutDrugAllergy.chipGroup,
            binding.layoutFoodAllergy.chipGroup,
            binding.layoutOtherAllergy.chipGroup,
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

        lifecycleScope.launch {
            viewModel.allergyList.collectLatest {
                updateChipsFromDiseaseList(it)
            }
        }

        binding.btnBack.setOnClickListener { navController.popBackStack() }
        binding.btnComplete.setOnClickListener {
            with(binding.layoutDrugAllergy.chipGroup) {
                val checkedChipList = checkedChipIds
                checkedChipList.forEach {
                    val chip = findViewById<Chip>(it)
                    allergyList.add(chip.text.toString())
                }
            }
            with(binding.layoutFoodAllergy.chipGroup) {
                val checkedChipList = checkedChipIds
                checkedChipList.forEach {
                    val chip = findViewById<Chip>(it)
                    allergyList.add(chip.text.toString())
                }
            }
            with(binding.layoutOtherAllergy.chipGroup) {
                val checkedChipList = checkedChipIds
                checkedChipList.forEach {
                    val chip = findViewById<Chip>(it)
                    allergyList.add(chip.text.toString())
                }
            }
            viewModel.setAllergyInfo(allergyList)
            navController.navigate(R.id.myHealthInfoFragment)
        }
    }
}
