package com.eco.route.feature.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.commit
import com.eco.route.R
import com.eco.route.feature.app.App
import com.eco.route.feature.map.WorkMaps
import com.google.android.gms.maps.SupportMapFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}