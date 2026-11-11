package com.androidbook.bookmarket

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MenuActivity : AppCompatActivity() {
    private var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.menu)) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }
    }

    fun onBookListClick(view: View) {
        startActivity(Intent(this, BooksActivity::class.java))
    }

    fun onEBookClick(view: View) = showMessage("E-Book 버튼을 클릭하였습니다")
    fun onCustomerInquiryClick(view: View) = showMessage("고객문의 버튼을 클릭하였습니다")
    fun onMyPageClick(view: View) = showMessage("나의페이지 버튼을 클릭하였습니다")
    fun onMarketInfoClick(view: View) = showMessage("마켓정보 버튼을 클릭하였습니다")
    fun onSettingClick(view: View) = showMessage("설정 버튼을 클릭하였습니다")

    fun onBannerClick(view: View) {
        val banner = findViewById<ImageView>(R.id.imageView2)
        banner.setImageResource(if (index % 2 == 0) R.drawable.menu_banner2 else R.drawable.menu_banner1)
        index++
    }

    private fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }
}
