package com.androidbook.customdialogproject

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var tvResult: TextView
    private lateinit var tvInput: TextView
    private var firstNumber = 0.0
    private var operation: String? = null
    private var newNumber = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }
        findViewById<Button>(R.id.button).setOnClickListener {
            val content = layoutInflater.inflate(R.layout.dialog_calculate, null)
            tvResult = content.findViewById(R.id.tvResult)
            tvInput = content.findViewById(R.id.tvIntput)
            firstNumber = 0.0
            operation = null
            newNumber = true
            val dialog = AlertDialog.Builder(this).setView(content).show()
            content.findViewById<Button>(R.id.buttonclose).setOnClickListener { dialog.dismiss() }
        }
    }

    fun numClick(view: View) {
        if (newNumber) tvResult.text = ""
        tvResult.append(view.tag.toString())
        newNumber = false
    }

    fun BackClick(view: View) {
        tvResult.text = tvResult.text.toString().dropLast(1)
    }

    fun ClearClick(view: View) {
        tvResult.text = ""
        newNumber = true
    }

    fun ACClick(view: View) {
        ClearClick(view)
        tvInput.text = ""
        firstNumber = 0.0
        operation = null
    }

    fun DotClick(view: View) {
        if (newNumber) {
            tvResult.text = "0."
            newNumber = false
        } else if (!tvResult.text.contains('.')) tvResult.append(".")
    }

    fun OperClick(view: View) {
        val current = tvResult.text.toString().toDoubleOrNull()
        if (current != null) firstNumber = current
        operation = view.tag.toString()
        tvInput.text = "${format(firstNumber)} $operation"
        newNumber = true
    }

    fun EqualClick(view: View) {
        val secondNumber = tvResult.text.toString().toDoubleOrNull() ?: return
        val operator = operation ?: return
        val result = when (operator) {
            "+" -> firstNumber + secondNumber
            "-" -> firstNumber - secondNumber
            "x" -> firstNumber * secondNumber
            "÷" -> firstNumber / secondNumber
            else -> return
        }
        tvInput.text = "${format(firstNumber)} $operator ${format(secondNumber)} = ${format(result)}"
        tvResult.text = format(result)
        firstNumber = result
        operation = null
        newNumber = true
    }

    private fun format(value: Double): String =
        if (value.isFinite() && value % 1.0 == 0.0) value.toLong().toString() else value.toString()
}
