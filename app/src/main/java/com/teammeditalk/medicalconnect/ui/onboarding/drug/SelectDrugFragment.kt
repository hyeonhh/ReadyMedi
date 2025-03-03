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

    override fun onBindLayout() {
        super.onBindLayout()

        if (chipIds.isNotEmpty()) {
            binding.layoutDrugDuration.layout.visibility = View.VISIBLE
        }

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
                    drugList.add(chip.text.toString())
                }
                if (selectedDate != null) viewModel.saveUserDrugStartDate(selectedDate!!)
                viewModel.saveUserDrugDuration(
                    binding.layoutDrugDuration.layoutDrugDuration.editText.text
                        .toString(),
                )
                viewModel.saveUserDrugCount(
                    binding.layoutDrugDuration.layoutDrugInputCount.editText.text
                        .toString(),
                )
            }
            viewModel.saveUserDrug(drugList)
            findNavController().navigate(R.id.allergySelectFragment)
        }
    }

    override fun onDateSelected(date: CalendarDay) {
        selectedDate = "${date.year}-${date.month}-${date.day}"
        binding.layoutDrugStart.drugExposedDropDownMenu.setTvTitle(selectedDate!!)
        binding.btnNext.setBackgroundColor(requireContext().getColor(R.color.blue50))
        binding.btnNext.setTextColor(requireContext().getColor(R.color.white))
    }
}
