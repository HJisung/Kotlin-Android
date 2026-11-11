package com.androidbook.bookmarket

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        findViewById<TextView>(R.id.cart_total_price).text = Cart.total().toString()
        findViewById<RecyclerView>(R.id.cart_recyclerview).apply {
            layoutManager = LinearLayoutManager(this@CartActivity)
            adapter = OrderAdapter()
        }
        findViewById<Button>(R.id.cart_order_button).setOnClickListener {
            startActivity(Intent(this, ShippingActivity::class.java))
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) { finish(); return true }
        return super.onOptionsItemSelected(item)
    }
}
