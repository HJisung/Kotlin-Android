package com.androidbook.bookmarket

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MyPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)
        val content = findViewById<android.view.View>(R.id.mypage)
        content.post {
            val bars = ViewCompat.getRootWindowInsets(content)
                ?.getInsets(WindowInsetsCompat.Type.systemBars()) ?: return@post
            content.setPadding(content.paddingLeft, bars.top, content.paddingRight, bars.bottom)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        findViewById<Button>(R.id.meno).setOnClickListener {
            startActivity(Intent(this, MemoActivity::class.java))
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) { finish(); return true }
        return super.onOptionsItemSelected(item)
    }
}
