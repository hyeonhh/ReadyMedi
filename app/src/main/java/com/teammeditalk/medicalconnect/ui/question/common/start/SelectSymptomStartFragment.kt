package com.teammeditalk.medicalconnect.ui.question.common.start

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentSelectSymptomStartBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import dagger.hilt.android.AndroidEntryPoint

// 2. 언제부터 증상이 발생했는지
@AndroidEntryPoint
class SelectSymptomStartFragment :
    BaseFragment<FragmentSelectSymptomStartBinding>(
        FragmentSelectSymptomStartBinding::inflate,
    ),
    DateSelectionListener {
    private val viewModel: QuestionViewModel by activityViewModels()
    private val navController by lazy { findNavController() }

    private var selectedDate: String? = null

    override fun onBindLayout() {
        super.onBindLayout()

        binding.exposedDropDownMenu.setOnClickListener {
            val calendarBottomSheet = CalendarBottomSheetFragment.newInstance()
            calendarBottomSheet.setDateSelectionListener(this)
            calendarBottomSheet.show(parentFragmentManager, "CalendarBottomSheetFragment")
        }
        binding.btnBack.setOnClickListener { navController.popBackStack() }
        binding.btnNext.setOnClickListener {
            if (selectedDate != null) {
                viewModel.selectSymptomStartDate(selectedDate!!)
            }

            navController.navigate(R.id.selectPainTypeFragment)
        }
    }

    override fun onDateSelected(date: CalendarDay) {
        selectedDate = "${date.year}-${date.month}-${date.day}"
        binding.exposedDropDownMenu.setTvTitle(selectedDate!!)
        binding.btnNext.setBackgroundColor(requireContext().getColor(R.color.blue50))
        binding.btnNext.setTextColor(requireContext().getColor(R.color.white))
    }
}
