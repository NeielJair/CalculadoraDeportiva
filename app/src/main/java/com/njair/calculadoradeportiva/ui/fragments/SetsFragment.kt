package com.njair.calculadoradeportiva.ui.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.njair.calculadoradeportiva.R
import com.njair.calculadoradeportiva.views.TimePickerView
import kotlin.math.roundToInt

class SetsFragment : Fragment() {
    private var expectedTime: Float = 0f
    private var percentage: Int = 0
    private var goalTime: Float = 0f

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_sets, container, false)
        val tvExpectedTime = root.findViewById<TextView>(R.id.tv_expected_time)
        val etPercentage = root.findViewById<EditText>(R.id.et_percentage)
        val tvGoalTime = root.findViewById<TextView>(R.id.tv_goal_time)

        fun timeToMinutes(time: Float): Int = (time/60).toInt()
        fun timeToSeconds(time: Float): Int = time.toInt() % 60
        fun timeToTenths(time: Float): Int = ((time - time.toInt()) * 10).roundToInt()

        fun updateGoal() {
            goalTime = if (percentage == 0) 0f else expectedTime * 100f/percentage
            tvGoalTime.text =
                "${timeToMinutes(goalTime)}' " +
                "${timeToSeconds(goalTime)}'' " +
                "${timeToTenths(goalTime)}"
        }

        tvExpectedTime.setOnClickListener {
            val dialog: AlertDialog.Builder = AlertDialog.Builder(context)
            val timePicker = TimePickerView(
                context,
                timeToMinutes(expectedTime),
                timeToSeconds(expectedTime),
                timeToTenths(expectedTime)
            )
            dialog.setView(timePicker)
            dialog.setPositiveButton(android.R.string.ok) { _, _ ->
                expectedTime = timePicker.npMinutes.value * 60f +
                               timePicker.npSeconds.value +
                               timePicker.npTenths.value / 10f

                tvExpectedTime.text =
                    "${timeToMinutes(expectedTime)}' " +
                    "${timeToSeconds(expectedTime)}'' " +
                    "${timeToTenths(expectedTime)}"

                updateGoal()
            }
            dialog.setNegativeButton(android.R.string.cancel) { _, _ -> }
            dialog.show()
        }

        etPercentage.doOnTextChanged { s, _, _, _ ->
            percentage = if (s.isNullOrBlank()) 0 else s.toString().toInt()
            updateGoal()
        }

        updateGoal()
        return root
    }
}