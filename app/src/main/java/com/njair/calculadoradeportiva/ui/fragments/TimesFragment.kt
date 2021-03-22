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
import kotlin.math.pow
import kotlin.math.roundToInt

class TimesFragment : Fragment() {
    private val velDecimals = 2

    private var distanceRun: Int = 0
    private var timeAchieved: Float = 0f
    private var goalDistance: Int = 0
    private var goalTime: Float = 0f

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_times, container, false)
        val etDistanceRun = root.findViewById<EditText>(R.id.et_distance_run)
        val tvTimeAchieved = root.findViewById<TextView>(R.id.tv_time_achieved)
        val etGoalDistance = root.findViewById<EditText>(R.id.et_goal_distance)
        val tvGoalTime = root.findViewById<TextView>(R.id.tv_goal_time)
        val tvApproxVel = root.findViewById<TextView>(R.id.tv_approximated_velocity)

        fun timeToMinutes(time: Float): Int = (time/60).toInt()
        fun timeToSeconds(time: Float): Int = time.toInt() % 60
        fun timeToTenths(time: Float): Int = ((time - time.toInt()) * 10).roundToInt()

        fun updateGoal() {
            goalTime = if (distanceRun == 0) 0f else  goalDistance * timeAchieved / distanceRun
            tvGoalTime.text =
                "${timeToMinutes(goalTime)}' " +
                "${timeToSeconds(goalTime)}'' " +
                "${timeToTenths(goalTime)}"
        }

        fun updateVelocity() {
            val power = 10f.pow(velDecimals)
            tvApproxVel.text =
                if (timeAchieved == 0f)
                    "0 ㎧"
                else
                    "${(distanceRun / timeAchieved * power).roundToInt() / power} ㎧"
        }

        etDistanceRun.doOnTextChanged { s, _, _, _ ->
            distanceRun = if (s.isNullOrBlank()) 0 else s.toString().toInt()
            updateGoal()
            updateVelocity()
        }

        tvTimeAchieved.setOnClickListener {
            val dialog: AlertDialog.Builder = AlertDialog.Builder(context)
            val timePicker = TimePickerView(
                context,
                timeToMinutes(timeAchieved),
                timeToSeconds(timeAchieved),
                timeToTenths(timeAchieved)
            )
            dialog.setView(timePicker)
            dialog.setPositiveButton(android.R.string.ok) { _, _ ->
                timeAchieved = timePicker.npMinutes.value * 60f +
                        timePicker.npSeconds.value +
                        timePicker.npTenths.value / 10f

                tvTimeAchieved.text =
                    "${timeToMinutes(timeAchieved)}' " +
                    "${timeToSeconds(timeAchieved)}'' " +
                    "${timeToTenths(timeAchieved)}"

                updateGoal()
                updateVelocity()
            }
            dialog.setNegativeButton(android.R.string.cancel) { _, _ -> }
            dialog.show()
        }

        etGoalDistance.doOnTextChanged { s, _, _, _ ->
            goalDistance = if (s.isNullOrBlank()) 0 else s.toString().toInt()
            updateGoal()
        }

        updateGoal()
        updateVelocity()
        return root
    }
}