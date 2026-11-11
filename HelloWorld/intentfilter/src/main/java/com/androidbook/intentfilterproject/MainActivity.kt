package com.androidbook.intentfilterproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }

        val recipient = findViewById<EditText>(R.id.editText1)
        val subject = findViewById<EditText>(R.id.editText2)
        val content = findViewById<EditText>(R.id.editText3)

        findViewById<Button>(R.id.button).setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "message/rfc822"
                putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient.text.toString()))
                putExtra(Intent.EXTRA_SUBJECT, subject.text.toString())
                putExtra(Intent.EXTRA_TEXT, content.text.toString())
            }
            startActivity(Intent.createChooser(intent, "앱을 선택하세요"))
        }
    }
}
