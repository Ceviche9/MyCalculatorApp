package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private var tvResult: TextView? = null
    private var lastNumeric: Boolean = false
    private var lastDot : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvResult = findViewById(R.id.tvResult)
    }

    fun onDigit(view: View) {
        // Eu preciso dizer que a view é um botão para pegar a propriedade de text.
        tvResult?.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }

    fun onClear(view: View) {
        tvResult?.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot) {
            tvResult?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View) {
        tvResult?.text?.let {
            if (lastNumeric && !isOperatorAdded(it.toString())) {
                tvResult?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }

    fun onEqual(view: View) {
        if(lastNumeric) {
            var tvValue = tvResult?.text.toString()
            var prefix = ""
            try {
                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    // Retirar o primeiro valor da string
                    tvValue = tvValue.substring(-1)
                }
                if (tvValue.contains("-")) {
                    val splitValue = tvValue.split("-")

                    var leftSideValue = splitValue[0]
                    var rightSideValue = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        leftSideValue = prefix + leftSideValue
                    }

                    tvResult?.text = removeZeroAfterDot((leftSideValue.toDouble() - rightSideValue.toDouble()).toString())
                } else if (tvValue.contains("+")) {
                    val splitValue = tvValue.split("+")

                    var leftSideValue = splitValue[0]
                    var rightSideValue = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        leftSideValue = prefix + leftSideValue
                    }

                    tvResult?.text = removeZeroAfterDot((leftSideValue.toDouble() + rightSideValue.toDouble()).toString())
                } else if (tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")

                    var leftSideValue = splitValue[0]
                    var rightSideValue = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        leftSideValue = prefix + leftSideValue
                    }

                    tvResult?.text = removeZeroAfterDot((leftSideValue.toDouble() / rightSideValue.toDouble()).toString())
                } else if (tvValue.contains("*")) {
                    val splitValue = tvValue.split("*")

                    var leftSideValue = splitValue[0]
                    var rightSideValue = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        leftSideValue = prefix + leftSideValue
                    }

                    tvResult?.text = removeZeroAfterDot((leftSideValue.toDouble() * rightSideValue.toDouble()).toString())
                }


            } catch (error: java.lang.ArithmeticException) {
                error.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result: String): String {
        var value = result
        if (result.contains(".0")) {
            value = result.substring(0, result.length - 2)
        }

        return value
    }

    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/") ||
                    value.contains("*") ||
                    value.contains("+") ||
                    value.contains("-")
        }
    }
}