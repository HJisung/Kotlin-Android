package com.androidbook.bookmarket

import android.os.Bundle
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
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
                startActivity(Intent(this, BookActivity::class.java).putExtra("bookid", when (id) {
                    R.id.book1 -> "BOOK1234"
                    R.id.book2 -> "BOOK1235"
                    R.id.book3 -> "BOOK1236"
                    else -> "BOOK1237"
                }))
            }
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_books, menu)
        val search = menu.findItem(R.id.menu_search).actionView as SearchView
        search.queryHint = "도서명을 입력해주세요."
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(applicationContext, query, Toast.LENGTH_LONG).show()
                return false
            }
            override fun onQueryTextChange(newText: String) = false
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) { finish(); return true }
        if (item.itemId == R.id.menu_home) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show()
        }
        return true
    }
}
