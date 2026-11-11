package com.androidbook.bookmarket

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BookActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.book)) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }
        val bookId = intent.getStringExtra("bookid") ?: "BOOK1234"
        val book = when (bookId) {
            "BOOK1235" -> listOf("난생처음 MySQL", "25000", "2024-01-17", "배은영", "SQL의 기초와 쿼리 작성법을 배웁니다.")
            "BOOK1236" -> listOf("쉽게 배우는 PHP 웹 프로그래밍", "29000", "2024-06-30", "송미영", "PHP의 이론과 실습을 단계별로 배웁니다.")
            "BOOK1237" -> listOf("미라클 HTML5+CSS3+자바스크립트", "31000", "2024-07-11", "조성호", "다채로운 예제로 웹 프로그래밍을 배웁니다.")
            else -> listOf("쉽게 배우는 알고리즘", "32000", "2024-01-03", "문병로", "알고리즘과 자료구조를 배웁니다.")
        }
        val image = when (bookId) {
            "BOOK1235" -> R.drawable.books_book2
            "BOOK1236" -> R.drawable.books_book3
            "BOOK1237" -> R.drawable.books_book4
            else -> R.drawable.books_book1
        }
        findViewById<ImageView>(R.id.imageView1).setImageResource(image)
        val fields = listOf(R.id.book_tv01, R.id.book_tv02, R.id.book_tv03, R.id.book_tv04,
            R.id.book_tv05, R.id.book_tv06, R.id.book_tv07)
        val values = listOf(bookId) + book + "IT/모바일"
        fields.zip(values).forEach { (id, value) -> findViewById<TextView>(id).text = value }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_book, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home, R.id.menu_list -> finish()
            R.id.menu_home -> startActivity(Intent(this, MainActivity::class.java))
            else -> Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show()
        }
        return true
    }
}
