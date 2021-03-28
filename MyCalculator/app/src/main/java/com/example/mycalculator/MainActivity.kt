package com.example.mycalculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var lastNumeric: Boolean = false
    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }


    fun onDigit(view: View) {
        tvInput.append((view as Button).text)
        lastNumeric = true
    }

    fun onClear(view: View) {
        tvInput.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot) {
            tvInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View) {
        if (lastNumeric && !isOperatorAdded(tvInput.text.toString())) {
            tvInput.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    fun onEqual(view: View) {
        if (lastNumeric) {

            var tvValue = tvInput.text.toString()
            var prefix = ""

            try {

                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }

                if (tvValue.contains("-")) {
                    val splitValue = tvValue.split("-")

                    var valueOne = splitValue[0]
                    var valueTwo = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        valueOne = prefix + valueOne
                    }

                    tvInput.text = removeZeroAfterDot((valueOne.toDouble() - valueTwo.toDouble()).toString())

                } else if (tvValue.contains("+")) {
                    val splitValue = tvValue.split("+")

                    var valueOne = splitValue[0]
                    var valueTwo = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        valueOne = prefix + valueOne
                    }

                    tvInput.text = removeZeroAfterDot((valueOne.toDouble() + valueTwo.toDouble()).toString())

                } else if (tvValue.contains("X")) {
                    val splitValue = tvValue.split("X")

                    var valueOne = splitValue[0]
                    var valueTwo = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        valueOne = prefix + valueOne
                    }

                    tvInput.text = removeZeroAfterDot((valueOne.toDouble() * valueTwo.toDouble()).toString())

                } else if (tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")

                    var valueOne = splitValue[0]
                    var valueTwo = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        valueOne = prefix + valueOne
                    }

                    tvInput.text = removeZeroAfterDot((valueOne.toDouble() / valueTwo.toDouble()).toString())
                }

            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/") || value.contains("X") || value.contains("+") || value.contains("-")
        }
    }

    private fun removeZeroAfterDot(result: String): String {
        var value = result
        val splitResult = result.split(".0")
        if (result.contains(".0")) {
            if (splitResult[1].isEmpty()) {
                value = result.substring(0, result.length - 2)
            }
        }

        return value
    }
}