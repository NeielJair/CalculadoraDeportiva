package com.njair.calculadoradeportiva.views

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import android.widget.NumberPicker
import com.njair.calculadoradeportiva.R

class TimePickerView(context: Context?, minutes: Int, seconds: Int, tenths: Int) :
    LinearLayout(context) {
    val npMinutes: NumberPicker by lazy<NumberPicker> { findViewById(R.id.np_minutes) }
    val npSeconds: NumberPicker by lazy<NumberPicker> { findViewById(R.id.np_seconds) }
    val npTenths: NumberPicker by lazy<NumberPicker> { findViewById(R.id.np_tenths) }



    init {
        View.inflate(context, R.layout.dialog_time_picker, this)
        npMinutes.minValue = 0
        npMinutes.maxValue = 120
        npMinutes.value = minutes

        npSeconds.minValue = 0
        npSeconds.maxValue = 59
        npSeconds.value = seconds

        npTenths.minValue = 0
        npTenths.maxValue = 9
        npTenths.value = tenths
    }
}