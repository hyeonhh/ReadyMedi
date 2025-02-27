package com.teammeditalk.medicalconnect.ui.question.common.start.decorator

import android.content.Context
import android.text.style.ForegroundColorSpan
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.teammeditalk.medicalconnect.R

class TodayDecorator(
    private val context: Context,
) : com.prolificinteractive.materialcalendarview.DayViewDecorator {
    private var date = CalendarDay.today()

    override fun shouldDecorate(day: CalendarDay?): Boolean = day == date

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(object : ForegroundColorSpan(context.resources.getColor(R.color.blue50)) {})
    }
}
