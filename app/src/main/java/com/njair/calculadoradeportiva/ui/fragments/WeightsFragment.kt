package com.njair.calculadoradeportiva.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.njair.calculadoradeportiva.R

class WeightsFragment : Fragment() {
    private var expectedWeight: Float = 0f
    private var percentage: Int = 0
    private var goalWeight: Float = 0f

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_weights, container, false)
        val etKilograms = root.findViewById<EditText>(R.id.et_kilograms)
        val etGrams = root.findViewById<EditText>(R.id.et_grams)
        val etPercentage = root.findViewById<EditText>(R.id.et_percentage)
        val tvGoalWeight = root.findViewById<TextView>(R.id.tv_goal_weight)

        fun weightToKg(weight: Float): Int = weight.toInt()
        fun weightToGr(weight: Float): Int = ((weight - weight.toInt()) * 1000).toInt()

        fun updateWeight() {
            val strKg = etKilograms.text.toString()
            val strGr = etGrams.text.toString()

            expectedWeight = if (strKg.isBlank())
                if (strGr.isBlank())
                    0f
                else
                    strGr.toFloat() / 1000f
            else
                if (strGr.isBlank())
                    strKg.toFloat()
                else
                    strKg.toFloat() + strGr.toFloat() / 1000f
        }

        fun updateGoal() {
            goalWeight = expectedWeight * percentage/100f
            tvGoalWeight.text = "${weightToKg(goalWeight)} kg   ${weightToGr(goalWeight)} gr"
        }

        etKilograms.doOnTextChanged { _, _, _, _ ->
            updateWeight()
            updateGoal()
        }

        etGrams.doOnTextChanged { _, _, _, _ ->
            updateWeight()
            updateGoal()
        }

        etPercentage.doOnTextChanged { s, _, _, _ ->
            percentage = if (s.isNullOrBlank()) 0 else s.toString().toInt()
            updateGoal()
        }

        updateWeight()
        updateGoal()
        return root
    }
}