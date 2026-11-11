package com.androidbook.bookmarket

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        findViewById<TextView>(R.id.textViewDate).text = "배송일 : ${intent.getStringExtra("date").orEmpty()}"
        findViewById<TextView>(R.id.textViewName).text = "성명 : ${intent.getStringExtra("name").orEmpty()} (${intent.getStringExtra("phone").orEmpty()})"
        findViewById<TextView>(R.id.textViewZipcode).text = "우편번호 : ${intent.getStringExtra("zipcode").orEmpty()}"
        findViewById<TextView>(R.id.textViewAddress).text = "주소 : ${intent.getStringExtra("address").orEmpty()}"
        findViewById<TextView>(R.id.textViewTotal).text = Cart.total().toString()
        findViewById<RecyclerView>(R.id.order_recyclerview).apply {
            layoutManager = LinearLayoutManager(this@OrderActivity)
            adapter = OrderAdapter()
        }
        findViewById<Button>(R.id.buttonSubmit).setOnClickListener {
            AlertDialog.Builder(this).setTitle("도서 주문 완료")
                .setMessage("도서를 주문해 주셔서 감사합니다.")
                .setIcon(R.drawable.icon_order)
                .setPositiveButton("예") { _, _ ->
                    Cart.books.clear()
                    startActivity(Intent(this, MainActivity::class.java))
                }
                .setNegativeButton("아니요", null).show()
        }
        findViewById<Button>(R.id.buttonCancel).setOnClickListener { finish() }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) { finish(); return true }
        return super.onOptionsItemSelected(item)
    }
}
