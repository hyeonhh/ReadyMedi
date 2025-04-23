package com.teammeditalk.medicalconnect.ui.edit.drug

import androidx.core.view.children
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentEditDrugBinding
import com.teammeditalk.medicalconnect.ui.util.CalendarBottomSheetFragment
import com.teammeditalk.medicalconnect.ui.util.DateSelectionListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class EditDrugFragment :
    BaseFragment<FragmentEditDrugBinding>(
        FragmentEditDrugBinding::inflate,
    ),
    DateSelectionListener {
    private val navController by lazy { findNavController() }
    private val viewModel: EditDrugViewModel by viewModels()
    private var drugList: MutableList<String> = mutableListOf()
    private var selectedDate = ""

    override fun onDestroyView() {
        Timber.d("onDestroyView")
        super.onDestroyView()
    }

    override fun onDateSelected(date: CalendarDay) {
        selectedDate = "${date.year}-${date.month}-${date.day}"
        binding.layoutDrugStart.drugExposedDropDownMenu.setTvTitle(selectedDate)
    }

    override fun onBindLayout() {
        super.onBindLayout()

        binding.layoutDrugStart.drugExposedDropDownMenu.setOnClickListener {
            val calendarBottomSheet = CalendarBottomSheetFragment.newInstance()
            calendarBottomSheet.setDateSelectionListener(this)
            calendarBottomSheet.show(parentFragmentManager, "CalendarBottomSheetFragment")
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // 현재 저장된 약 정보 불러오기
                viewModel.drugList.collectLatest { list ->
                    Timber.d("list 변경 :$list")
                    // 불러온 약 정보를 표시해주기
                    binding.chipGroup.children.forEach {
                        val chip = it as? Chip
                        // 상태 업데이트만 하고 리스너는 건드리지 않음
                        chip?.isChecked = list.contains(chip?.text.toString())
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.drugCount.collectLatest {
                    binding.layoutDrugDuration.layoutDrugInputCount.editText
                        .setText(it.toString())
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.drugDuration.collectLatest {
                    val duration = binding.layoutDrugDuration.layoutDrugDuration.editText
                    duration.setText(it)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.drugStartDate.collectLatest {
                    Timber.d("시작 :$it")
                    val startDate = binding.layoutDrugStart.drugExposedDropDownMenu
                    startDate.setTvTitle(it)
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
                    drugList.add(chip.text.toString())
                }
            }

            viewModel.saveUserDrug(drugList)
            viewModel.saveUserDrugCount(
                binding.layoutDrugDuration.layoutDrugInputCount.editText.text
                    .toString(),
            )
            viewModel.saveUserDrugDuration(
                binding.layoutDrugDuration.layoutDrugDuration.editText.text
                    .toString(),
            )
            viewModel.saveUserDrugStartDate(selectedDate)
            navController.navigate(R.id.myHealthInfoFragment)
        }
    }
}
