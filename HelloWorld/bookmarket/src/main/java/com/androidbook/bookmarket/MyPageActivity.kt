package com.androidbook.bookmarket

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.File

class MyPageActivity : AppCompatActivity() {
    private val profileFile get() = File(filesDir, "profile_photo.png")
    private val choosePhoto = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            try {
                contentResolver.openInputStream(uri)?.use { input ->
                    profileFile.outputStream().use { output -> input.copyTo(output) }
                }
                showProfile()
            } catch (_: Exception) {
                Toast.makeText(this, "사진을 불러올 수 없습니다", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private val takePhoto = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        if (bitmap != null) {
            profileFile.outputStream().use { bitmap.compress(Bitmap.CompressFormat.PNG, 100, it) }
            showProfile()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)
        val content = findViewById<android.view.View>(R.id.mypage)
        content.post {
            val bars = ViewCompat.getRootWindowInsets(content)
                ?.getInsets(WindowInsetsCompat.Type.systemBars()) ?: return@post
            content.setPadding(content.paddingLeft, bars.top, content.paddingRight, bars.bottom)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        findViewById<Button>(R.id.meno).setOnClickListener {
            startActivity(Intent(this, MemoActivity::class.java))
        }
        showProfile()
        findViewById<View>(R.id.fab_add_photo).setOnClickListener {
            val content = layoutInflater.inflate(R.layout.activity_mypage_picture_dialog, null)
            val dialog = AlertDialog.Builder(this).setTitle("선택하세요")
                .setView(content).setNegativeButton("취소", null).show()
            content.findViewById<View>(R.id.layoutCameraPick).setOnClickListener {
                dialog.dismiss()
                takePhoto.launch(null)
            }
            content.findViewById<View>(R.id.layoutGalleryPick).setOnClickListener {
                dialog.dismiss()
                choosePhoto.launch("image/*")
            }
        }
    }

    private fun showProfile() {
        if (profileFile.exists()) {
            BitmapFactory.decodeFile(profileFile.absolutePath)?.let {
                findViewById<ImageView>(R.id.loginimg).setImageBitmap(it)
            }
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) { finish(); return true }
        return super.onOptionsItemSelected(item)
    }
}
