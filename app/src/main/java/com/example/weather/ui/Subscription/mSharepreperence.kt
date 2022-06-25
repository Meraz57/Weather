package com.example.weather.ui.Subscription

import android.content.SharedPreferences

object mSharepreperence {
    private var mSharedPref: SharedPreferences? = null
    const val TIME_24="time24"
    fun getTimeFormat():String{
        return mSharedPref!!.getString("time_format", TIME_24)!!
    }



}