package com.example.redballtoy.daytodayofnasa.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

//@JvmOverloads - to automatically override Java constructors
//to which class is AppCompatImageView
class EquilateralImageView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    //callback that comes when the picture is to be measured
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //override height using width as dimension
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}
