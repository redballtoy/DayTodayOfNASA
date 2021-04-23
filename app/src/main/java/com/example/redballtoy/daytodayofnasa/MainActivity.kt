package com.example.redballtoy.daytodayofnasa

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.redballtoy.daytodayofnasa.view.PictureOfTheDayFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //set theme
        val sharedPreferences = getSharedPreferences("Theme", Context.MODE_PRIVATE)
        val themeName = sharedPreferences.getString("ThemeName","Light")
        when(themeName){
            "Light" ->  setTheme(R.style.Theme_DayTodayOfNASA)
            "Dark" ->  setTheme(R.style.Theme_DayTodayOfNASA)
            "Indigo" ->  setTheme(R.style.IndigoTheme)
            "Pink" ->  setTheme(R.style.PinkTheme)
        }
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commitNow()
        }
    }
}