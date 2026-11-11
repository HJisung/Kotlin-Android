package com.androidbook.bookmarket

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MarketInfoActivity : AppCompatActivity(), OnMapReadyCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marketinfo)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val content = findViewById<View>(R.id.marketinfo)
        content.post {
            val bars = ViewCompat.getRootWindowInsets(content)
                ?.getInsets(WindowInsetsCompat.Type.systemBars()) ?: return@post
            content.setPadding(content.paddingLeft, bars.top, content.paddingRight, bars.bottom)
        }
        val map = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        map.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        val location = LatLng(37.5591022, 126.9278237)
        map.addMarker(MarkerOptions().position(location).title("한빛아카데미")
            .snippet("서울 서대문구 연희로2길 62")
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.hanbit_location)))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
        map.uiSettings.isZoomControlsEnabled = true
        map.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
            override fun getInfoWindow(marker: Marker): View? = null
            override fun getInfoContents(marker: Marker): View {
                val info = layoutInflater.inflate(R.layout.marketinfo_marker, null)
                info.findViewById<TextView>(R.id.info).text =
                    "${marker.title}\n위도: ${marker.position.latitude}\n경도: ${marker.position.longitude}"
                return info
            }
        })
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) { finish(); return true }
        return super.onOptionsItemSelected(item)
    }
}
