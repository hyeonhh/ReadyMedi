package com.teammeditalk.medicalconnect.ui.question.common.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.teammeditalk.medicalconnect.databinding.FragmentCalendarBottomSheetBinding
import com.teammeditalk.medicalconnect.ui.question.common.start.decorator.ClickDateDecorator
import com.teammeditalk.medicalconnect.ui.question.common.start.decorator.TodayDecorator

// 1. 콜백 인터페이스 정의하기
interface DateSelectionListener {
    fun onDateSelected(date: CalendarDay)
}

class CalendarBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentCalendarBottomSheetBinding? = null
    private val binding get() = _binding!!
    private var selectedDate: CalendarDay? = null
    private var dateSelectionListener: DateSelectionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentCalendarBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val decorator = TodayDecorator(requireContext())
        binding.calendarView.addDecorator(decorator)

        binding.calendarView.setOnDateChangedListener { widget, date, selected ->
            selectedDate = date
            val clickedDecorator = ClickDateDecorator(context = requireContext(), clickedDate = date)
            binding.calendarView.addDecorator(clickedDecorator)
        }

        binding.btnNext.setOnClickListener {
            selectedDate?.let {
                dateSelectionListener?.onDateSelected(it)
                dismiss()
            }
        }
    }

    // 리스너 설정 메서드
    fun setDateSelectionListener(listener: DateSelectionListener) {
        this.dateSelectionListener = listener
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): CalendarBottomSheetFragment = CalendarBottomSheetFragment()
    }
}
