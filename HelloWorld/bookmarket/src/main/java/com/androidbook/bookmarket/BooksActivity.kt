package com.androidbook.bookmarket

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BooksActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_books)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.books)) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }

        val books = listOf(
            R.id.book1 to "쉽게 배우는 알고리즘",
            R.id.book2 to "난생 처음 MySQL",
            R.id.book3 to "쉽게 배우는 PHP 웹 프로그래밍",
            R.id.book4 to "미라클 HTML5 + CSS3 + 자바스크립트"
        )
        books.forEach { (id, title) ->
            findViewById<ImageView>(id).setOnClickListener {
                Toast.makeText(applicationContext, title, Toast.LENGTH_LONG).show()
            }
        }
    }
}
