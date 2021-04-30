package com.example.redballtoy.daytodayofnasa.ui.activities

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.redballtoy.daytodayofnasa.R
import com.example.redballtoy.daytodayofnasa.ui.fragments.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout

class ApiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_api)

        val viewPager: ViewPager = findViewById(R.id.vp_pager)
        viewPager.adapter = ViewPagerAdapter(supportFragmentManager)
        val tabLayout = findViewById<TabLayout>(R.id.tl_layout)
        tabLayout.setupWithViewPager(viewPager)

        //setNameIconToTab(tabLayout)

        setCustomTabs(tabLayout)
    }

    //создание пользовательского tab view мз отдельных шаблонов
    private fun setCustomTabs(tabLayout: TabLayout) {
        val layoutInflater = LayoutInflater.from(this)
        tabLayout.getTabAt(0)?.customView =
                layoutInflater.inflate(R.layout.activity_api_custom_tab_earth, null)
        tabLayout.getTabAt(1)?.customView =
                layoutInflater.inflate(R.layout.activity_api_custom_tab_mars, null)
        tabLayout.getTabAt(2)?.customView =
                layoutInflater.inflate(R.layout.activity_api_custom_tab_weather, null)
    }


    //добавление заголовков и иконок в таб
    private fun setNameIconToTab(tabLayout: TabLayout) {
        tabLayout.getTabAt(0)?.setText(R.string.earth)
        tabLayout.getTabAt(1)?.setText(R.string.mars)
        tabLayout.getTabAt(2)?.setText(R.string.weather)
        tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_earth)
        tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_mars)
        tabLayout.getTabAt(2)?.setIcon(R.drawable.ic_system)
    }


}