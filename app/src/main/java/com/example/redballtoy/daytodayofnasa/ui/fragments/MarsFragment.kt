package com.example.redballtoy.daytodayofnasa.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.redballtoy.daytodayofnasa.R

class MarsFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mars, container, false)
    }
    fun getFragmentTabName():String?{
        return  activity?.getString(R.string.mars)
    }
}