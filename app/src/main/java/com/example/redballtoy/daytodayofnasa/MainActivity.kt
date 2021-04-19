package com.example.redballtoy.daytodayofnasa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.redballtoy.daytodayofnasa.view.PictureOfTheDayFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commitNow()
        }
    }
}