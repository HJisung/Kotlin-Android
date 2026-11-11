package com.androidbook.bookmarket

import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BoardDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_detail)
        adjustBoardInsets(findViewById(R.id.boarddetail))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        listOf(R.id.textView_Title to "title", R.id.textView_UserID to "id",
            R.id.textView_Content to "content", R.id.textView_Date to "date").forEach { (id, key) ->
            findViewById<TextView>(id).text = intent.getStringExtra(key).orEmpty()
        }
        findViewById<Button>(R.id.button).setOnClickListener { finish() }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) { finish(); return true }
        return super.onOptionsItemSelected(item)
    }
}
