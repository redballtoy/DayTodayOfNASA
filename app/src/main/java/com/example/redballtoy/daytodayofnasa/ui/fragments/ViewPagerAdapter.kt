package com.example.redballtoy.daytodayofnasa.ui.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

//constants defining our fragments for fragment list
private const val EARTH_FRAGMENT = 0
private const val MARS_FRAGMENT = 1
private const val WEATHER_FRAGMENT = 2

class ViewPagerAdapter(
        private val fragmentManager: FragmentManager
) : FragmentStatePagerAdapter(fragmentManager) {

    //used hardcode don't use to real projects
    private val fragments = arrayOf(EarthFragment(), MarsFragment(), WeatherFragment())


    //called when it is necessary to get a fragment to display
    //in a real project, when receiving the id of a fragment, it must be instantiated
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> fragments[EARTH_FRAGMENT]
            1 -> fragments[MARS_FRAGMENT]
            2 -> fragments[WEATHER_FRAGMENT]
            else -> fragments[EARTH_FRAGMENT]
        }
    }

    override fun getCount(): Int {
        return fragments.size
    }

//    override fun getPageTitle(position: Int): CharSequence? {
//        return super.getPageTitle(position)
//    }
}