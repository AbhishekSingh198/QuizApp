package com.example.quizapp.utils

import com.example.quizapp.R

object IconPicker {
    val icons= arrayOf(R.drawable.ic_icon,R.drawable.ic_icon1,R.drawable.ic_icon2,R.drawable.ic_icon3,R.drawable.ic_icon4,R.drawable.ic_icon5,R.drawable.ic_icon6,R.drawable.ic_icon7)
    var currentIcon=0
    fun getIcon():Int {
        currentIcon = (currentIcon + 1) % icons.size
        return icons[currentIcon]
    }
}