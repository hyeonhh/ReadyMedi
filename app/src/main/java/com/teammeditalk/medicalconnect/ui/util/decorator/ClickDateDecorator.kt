package com.teammeditalk.medicalconnect.ui.util.decorator

import android.content.Context
import android.graphics.Color
import android.text.style.ForegroundColorSpan
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.teammeditalk.medicalconnect.R

class ClickDateDecorator(
    private val clickedDate: CalendarDay,
    private val context: Context,
) : DayViewDecorator {
    private val drawable = context.resources.getDrawable(R.drawable.rectangle_blue_12dp)

    override fun shouldDecorate(day: CalendarDay?): Boolean = clickedDate == day

    override fun decorate(view: DayViewFacade?) {
        view?.setBackgroundDrawable(drawable)
        view?.addSpan(object : ForegroundColorSpan(Color.WHITE) {})
    }
}
