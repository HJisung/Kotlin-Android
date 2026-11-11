package com.androidbook.bookmarket

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.SearchView
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MemoActivity : AppCompatActivity() {
    private lateinit var db: MemoDatabase
    private lateinit var list: ListView
    private var memos = ArrayList<HashMap<String, String>>()
    private var selected = -1
    private var searchText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo)
        val content = findViewById<android.view.View>(R.id.memo)
        content.post {
            val bars = ViewCompat.getRootWindowInsets(content)
                ?.getInsets(WindowInsetsCompat.Type.systemBars()) ?: return@post
            content.setPadding(content.paddingLeft, bars.top, content.paddingRight, bars.bottom)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        db = MemoDatabase(this)
        list = findViewById(R.id.memo_list)
        list.setOnItemClickListener { _, _, position, _ -> selected = position }
        findViewById<Button>(R.id.btnInsert).setOnClickListener { editMemo(null) }
        findViewById<Button>(R.id.btnUpdate).setOnClickListener {
            val memo = memos.getOrNull(selected)
            if (memo == null) showSelectMessage() else editMemo(memo)
        }
        findViewById<Button>(R.id.btnDelete).setOnClickListener {
            val memo = memos.getOrNull(selected)
            if (memo == null) showSelectMessage() else {
                AlertDialog.Builder(this).setTitle("메모장 삭제")
                    .setMessage("선택한 메모를 삭제하겠습니까?")
                    .setPositiveButton("예") { _, _ -> db.delete(memo.getValue("id")); refresh() }
                    .setNegativeButton("아니요", null).show()
            }
        }
        refresh()
    }

    private fun editMemo(memo: HashMap<String, String>?) {
        val content = layoutInflater.inflate(R.layout.activity_memo_dialog, null)
        val title = content.findViewById<EditText>(R.id.title)
        val body = content.findViewById<EditText>(R.id.content)
        title.setText(memo?.get("title").orEmpty())
        body.setText(memo?.get("content").orEmpty())
        val dialog = AlertDialog.Builder(this).setView(content).show()
        content.findViewById<Button>(R.id.insert).apply {
            if (memo != null) text = "수정"
            setOnClickListener {
                val date = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(Date())
                db.save(memo?.get("id"), date, title.text.toString(), body.text.toString())
                dialog.dismiss()
                refresh()
            }
        }
        content.findViewById<Button>(R.id.cancel).setOnClickListener { dialog.dismiss() }
    }

    private fun refresh() {
        selected = -1
        memos = db.read(searchText)
        list.adapter = SimpleAdapter(this, memos, R.layout.activity_memo_item,
            arrayOf("date", "title", "content"), intArrayOf(R.id.tvDate, R.id.tvTitle, R.id.tvContent))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val search = menu.findItem(R.id.search).actionView as SearchView
        search.queryHint = "메모 검색"
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchText = query
                refresh()
                search.clearFocus()
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) { searchText = ""; refresh() }
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) { finish(); return true }
        return super.onOptionsItemSelected(item)
    }
    override fun onDestroy() { db.close(); super.onDestroy() }
    private fun showSelectMessage() = Toast.makeText(this, "항목을 선택하세요", Toast.LENGTH_SHORT).show()
}
