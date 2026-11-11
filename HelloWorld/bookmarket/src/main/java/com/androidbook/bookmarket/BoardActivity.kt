package com.androidbook.bookmarket

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class BoardActivity : AppCompatActivity() {
    private lateinit var list: ListView
    private val posts = ArrayList<HashMap<String, String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)
        adjustBoardInsets(findViewById(R.id.board))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        list = findViewById(R.id.listView)
        list.setOnItemClickListener { _, _, position, _ ->
            val post = posts[position]
            startActivity(Intent(this, BoardDetailActivity::class.java).apply {
                listOf("title", "id", "content", "date").forEach { putExtra(it, post[it]) }
            })
        }
        findViewById<Button>(R.id.writeButton).setOnClickListener {
            startActivity(Intent(this, BoardWriteActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        thread {
            try {
                val connection = URL("http://10.0.2.2:8080/selectBoard.php").openConnection() as HttpURLConnection
                connection.connectTimeout = 4000
                connection.readTimeout = 4000
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                connection.disconnect()
                val array = JSONObject(response).getJSONArray("result")
                val loaded = ArrayList<HashMap<String, String>>()
                for (i in 0 until array.length()) {
                    val item = array.getJSONObject(i)
                    loaded.add(hashMapOf("title" to item.optString("title"), "id" to item.optString("id"),
                        "content" to item.optString("content"), "date" to item.optString("date")))
                }
                runOnUiThread {
                    posts.clear()
                    posts.addAll(loaded)
                    list.adapter = SimpleAdapter(this, posts, R.layout.activity_board_item,
                        arrayOf("title", "id", "date"),
                        intArrayOf(R.id.textView_Title, R.id.textView_UserID, R.id.textView_Date))
                }
            } catch (_: Exception) {
                runOnUiThread { Toast.makeText(this, "게시판 서버에 연결할 수 없습니다", Toast.LENGTH_LONG).show() }
            }
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) { finish(); return true }
        return super.onOptionsItemSelected(item)
    }
}

fun adjustBoardInsets(content: View) {
    content.post {
        val bars = ViewCompat.getRootWindowInsets(content)
            ?.getInsets(WindowInsetsCompat.Type.systemBars()) ?: return@post
        content.setPadding(content.paddingLeft, bars.top, content.paddingRight, bars.bottom)
    }
}
