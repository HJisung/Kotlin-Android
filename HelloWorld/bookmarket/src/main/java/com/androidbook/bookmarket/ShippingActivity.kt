package com.androidbook.bookmarket

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class ShippingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shipping)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val name = findViewById<EditText>(R.id.editTextName)
        val date = findViewById<EditText>(R.id.editTextDate)
        val phone = findViewById<EditText>(R.id.editTextPhone)
        val zipcode = findViewById<EditText>(R.id.editTextZipcode)
        val address = findViewById<EditText>(R.id.editTextAddress)

        date.setOnClickListener {
            val today = Calendar.getInstance()
            DatePickerDialog(this, { _, year, month, day ->
                date.setText("${year}년 ${month + 1}월 ${day}일")
            }, today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH)).show()
        }
        findViewById<Button>(R.id.buttonSubmit).setOnClickListener {
            startActivity(Intent(this, OrderActivity::class.java).apply {
                putExtra("name", name.text.toString())
                putExtra("date", date.text.toString())
                putExtra("phone", phone.text.toString())
                putExtra("zipcode", zipcode.text.toString())
                putExtra("address", address.text.toString())
            })
        }
        findViewById<Button>(R.id.buttonCancel).setOnClickListener { finish() }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) { finish(); return true }
        return super.onOptionsItemSelected(item)
    }
}
