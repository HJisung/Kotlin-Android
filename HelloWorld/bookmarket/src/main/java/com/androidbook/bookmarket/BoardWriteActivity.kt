package com.androidbook.bookmarket

import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import kotlin.concurrent.thread

class BoardWriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_write)
        adjustBoardInsets(findViewById(R.id.boardwrite))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        findViewById<Button>(R.id.buttonCancel).setOnClickListener { finish() }
        findViewById<Button>(R.id.buttonInsert).setOnClickListener {
            val title = findViewById<EditText>(R.id.editTextTitle).text.toString()
            val content = findViewById<EditText>(R.id.editTextContent).text.toString()
            if (title.isBlank() || content.isBlank()) {
                Toast.makeText(this, "제목과 내용을 입력하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            thread {
                try {
                    val data = mapOf("id" to "bookuser", "title" to title, "content" to content)
                        .entries.joinToString("&") { (key, value) -> "$key=${URLEncoder.encode(value, "UTF-8")}" }
                    val connection = URL("http://10.0.2.2:8080/insertBoard.php").openConnection() as HttpURLConnection
                    connection.requestMethod = "POST"
                    connection.connectTimeout = 4000
                    connection.readTimeout = 4000
                    connection.doOutput = true
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                    connection.outputStream.use { it.write(data.toByteArray(Charsets.UTF_8)) }
                    val success = connection.responseCode in 200..299
                    connection.disconnect()
                    runOnUiThread { if (success) finish() else Toast.makeText(this, "등록에 실패했습니다", Toast.LENGTH_SHORT).show() }
                } catch (_: Exception) {
                    runOnUiThread { Toast.makeText(this, "게시판 서버에 연결할 수 없습니다", Toast.LENGTH_LONG).show() }
                }
            }
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) { finish(); return true }
        return super.onOptionsItemSelected(item)
    }
}
