package com.teammeditalk.medicalconnect.ui.onboarding.drug

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentSelectDrugBinding
import com.teammeditalk.medicalconnect.ui.onboarding.OnBoardingViewModel
import com.teammeditalk.medicalconnect.ui.util.CalendarBottomSheetFragment
import com.teammeditalk.medicalconnect.ui.util.DateSelectionListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectDrugFragment :
    BaseFragment<FragmentSelectDrugBinding>(
        FragmentSelectDrugBinding::inflate,
    ),
    DateSelectionListener {
    private val drugList = mutableListOf<String>()
    private val viewModel: OnBoardingViewModel by activityViewModels()
    private var selectedDate: String? = null
    private var chipIds: List<Int> = emptyList()
    private val navController by lazy { findNavController() }

    override fun onBindLayout() {
        super.onBindLayout()

        binding.btnBack.setOnClickListener { navController.popBackStack() }

        binding.layoutDrugStart.drugExposedDropDownMenu.setOnClickListener {
            val calendarBottomSheet = CalendarBottomSheetFragment.newInstance()
            calendarBottomSheet.setDateSelectionListener(this)
            calendarBottomSheet.show(parentFragmentManager, "CalendarBottomSheetFragment")
        }

        binding.btnNext.setOnClickListener {
            with(binding.chipGroup) {
                chipIds = checkedChipIds
                checkedChipIds.forEach {
                    val chip = findViewById<Chip>(it)
                    drugList.add(chip.tag.toString())
                }
            }
            if (chipIds.isEmpty()) {
                binding.layoutLanguageWarn.layoutLanguageWarn.visibility = View.GONE
                saveDrugInfo()
                navController.navigate(R.id.allergySelectFragment)
            } else if (
                // 선택한 약이 있는 경우
                binding.layoutDrugDuration.layoutDrugDuration.editText.text
                    .isEmpty() or
                binding.layoutDrugDuration.layoutDrugInputCount.editText.text
                    .isEmpty() or
                (
                    binding.layoutDrugStart.drugExposedDropDownMenu
                        .getTvTitle()
                        .isEmpty()
                        and
                        binding.editText.text.isEmpty()
                )
            ) {
                binding.layoutLanguageWarn.layoutLanguageWarn.visibility = View.VISIBLE
            } else {
                binding.layoutLanguageWarn.layoutLanguageWarn.visibility = View.GONE
                saveDrugInfo()
                navController.navigate(R.id.allergySelectFragment)
            }
        }
    }

    private fun saveDrugInfo() {
        if (binding.editText.text.isNotEmpty()) {
            selectedDate = binding.editText.text.toString()
            viewModel.saveUserDrugStartDate(selectedDate!!)
            viewModel.saveUserDrug(drugList)
        } else if (binding.layoutDrugStart.drugExposedDropDownMenu
                .getTvTitle()
                .isNotEmpty()
        ) {
            selectedDate = binding.layoutDrugStart.drugExposedDropDownMenu.getTvTitle()
            viewModel.saveUserDrugStartDate(selectedDate!!)
            viewModel.saveUserDrug(drugList)
        }
        val duration = binding.layoutDrugDuration.layoutDrugDuration.editText.text
        val count = binding.layoutDrugDuration.layoutDrugInputCount.editText.text
        viewModel.saveUserDrugDuration(duration.toString())
        viewModel.saveUserDrugCount(count.toString())
    }

    override fun onDateSelected(date: CalendarDay) {
        selectedDate = "${date.year}-${date.month}-${date.day}"
        binding.layoutDrugStart.drugExposedDropDownMenu.setTvTitle(selectedDate!!)
        binding.btnNext.setBackgroundColor(requireContext().getColor(R.color.blue50))
        binding.btnNext.setTextColor(requireContext().getColor(R.color.white))
    }
}
