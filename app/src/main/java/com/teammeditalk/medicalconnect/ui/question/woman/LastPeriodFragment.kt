package com.teammeditalk.medicalconnect.ui.question.woman

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentLastPeriodBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import com.teammeditalk.medicalconnect.ui.util.CalendarBottomSheetFragment
import com.teammeditalk.medicalconnect.ui.util.DateSelectionListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LastPeriodFragment :
    BaseFragment<FragmentLastPeriodBinding>(
        FragmentLastPeriodBinding::inflate,
    ),
    DateSelectionListener {
    private val viewModel: QuestionViewModel by activityViewModels()
    private val navController by lazy { findNavController() }
    private var selectedDate: String = ""

    override fun onDateSelected(date: CalendarDay) {
        selectedDate = "${date.year}-${date.month}-${date.day}"
        binding.exposedDropDownMenu.setTvTitle(selectedDate!!)
        binding.btnNext.setBackgroundColor(requireContext().getColor(R.color.blue50))
        binding.btnNext.setTextColor(requireContext().getColor(R.color.white))
        binding.warn.layoutLanguageWarn.visibility = View.INVISIBLE
    }

    override fun onBindLayout() {
        super.onBindLayout()

        with(binding) {
            exposedDropDownMenu.setOnClickListener {
                val calendarBottomSheet = CalendarBottomSheetFragment.newInstance()
                calendarBottomSheet.setDateSelectionListener(this@LastPeriodFragment)
                calendarBottomSheet.show(parentFragmentManager, "CalendarBottomSheetFragment")
            }
            btnBack.setOnClickListener { navController.popBackStack() }
            btnNext.setOnClickListener {
                if (selectedDate == "") {
                    binding.warn.layoutLanguageWarn.visibility = View.VISIBLE
                } else {
                    binding.warn.layoutLanguageWarn.visibility = View.INVISIBLE

                    viewModel.setLastPeriod(selectedDate)
                    navController.navigate(R.id.cycleRegularityFragment)
                }
            }
        }
    }
}
