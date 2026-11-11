package com.androidbook.tablelayoutproject

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var etObjA: EditText
    private lateinit var etObjB: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }
        etObjA = findViewById(R.id.editText1)
        etObjB = findViewById(R.id.editText2)
    }

    fun onClickChoice(view: View) {
        val partA = etObjA.text.toString().toIntOrNull()
        val partB = etObjB.text.toString().toIntOrNull()
        val message = if (partA == 2 && partB == 5) "맞았습니다" else "틀렸습니다"

        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}
