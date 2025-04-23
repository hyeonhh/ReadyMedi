package com.teammeditalk.medicalconnect.ui.edit.family

import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentEditFamilyDiseaseBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditFamilyDiseaseFragment :
    BaseFragment<FragmentEditFamilyDiseaseBinding>(
        FragmentEditFamilyDiseaseBinding::inflate,
    ) {
    private val navController by lazy { findNavController() }
    private val viewModel: EditFamilyDiseaseViewModel by viewModels()
    private var familyDiseaseList: MutableList<String> = mutableListOf()

    override fun onBindLayout() {
        super.onBindLayout()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.familyDiseaseList.collectLatest { list ->
                    // 불러온 약 정보를 표시해주기
                    binding.chipGroup.children.forEach {
                        val chip = it as? Chip ?: return@forEach
                        // 상태 업데이트만 하고 리스너는 건드리지 않음
                        chip.isChecked = list.contains(chip.text.toString())
                    }
                }
            }
        }

        binding.btnBack.setOnClickListener { navController.popBackStack() }
        binding.btnComplete.setOnClickListener {
            // 선택된 칩 들을 drugList에 넣어주기
            with(binding.chipGroup) {
                val checkedChipList = checkedChipIds
                checkedChipList.forEach {
                    val chip = findViewById<Chip>(it)
                    familyDiseaseList.add(chip.text.toString())
                }
            }
            viewModel.setFamilyDiseaseList(familyDiseaseList)
            navController.navigate(R.id.myHealthInfoFragment)
        }
    }
}
