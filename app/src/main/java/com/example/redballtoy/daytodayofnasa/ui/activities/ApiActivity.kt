package com.example.redballtoy.daytodayofnasa.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.redballtoy.daytodayofnasa.R
import com.example.redballtoy.daytodayofnasa.ui.fragments.ViewPagerAdapter

class ApiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_api)

        val viewPagerAdapter: ViewPager = findViewById(R.id.vp_pager)
        viewPagerAdapter.adapter = ViewPagerAdapter(supportFragmentManager)

    }
}