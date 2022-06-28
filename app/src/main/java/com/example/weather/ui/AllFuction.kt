package com.example.weather.ui

import java.text.SimpleDateFormat
import java.util.*

 object AllFuction{

    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("HH:mm")
        return format.format(date)
    }
}