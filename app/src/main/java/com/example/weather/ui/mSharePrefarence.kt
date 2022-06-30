package com.example.weather.ui

import android.content.Context
import android.content.SharedPreferences
import com.example.weather.dataclass.data.nextWeek.ResponseNextWeek
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Address
import xyz.teamprojectx.weather.data.response.todayForecast.ResponseOneCall
import xyz.teamprojectx.weather.data.response.weather.ResponseWeather
import java.lang.reflect.Type

object mSharePrefarence {
    private var mSharedPref: SharedPreferences? = null
    private const val NAME = "com.example.weather.ui"

    fun init(context: Context) {
        if (mSharedPref == null) {
            mSharedPref = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
        }
    }

    fun read(key: String?, defValue: String?): String? {
        return mSharedPref!!.getString(key, defValue)
    }

    fun write(key: String?, value: String?) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putString(key, value)
        prefsEditor.apply()
    }


    fun writeInt(key: String?, value: Int) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putInt(key, value)
        prefsEditor.apply()
    }


    fun saveCurrentWeather(currentWeather: ResponseWeather) {
        val prefsEditor = mSharedPref!!.edit()
        val gson = Gson()
        val json: String = gson.toJson(currentWeather)
        prefsEditor.putString("current_weather", json)
        prefsEditor.apply()
    }

    fun getCurrentWeather():ResponseWeather? {
        val gson = Gson()
        val json: String? = mSharedPref!!.getString("current_weather", null)
        return if (json != null) {
            val type: Type = object : TypeToken<ResponseWeather>() {}.type
            gson.fromJson(json, type)
        }else{
            null
        }
    }

//    fun saveBlog(blogs:List<BlogData>) {
//        val prefsEditor = mSharedPref!!.edit()
//        val gson = Gson()
//        val json: String = gson.toJson(blogs)
//        prefsEditor.putString("blogs", json)
//        prefsEditor.apply()
//    }

//    fun getBlog():List<BlogData>?{
//        val gson = Gson()
//        val json: String? = mSharedPref!!.getString("blogs", null)
//        return if (json != null) {
//            val type: Type = object : TypeToken<List<BlogData>>() {}.type
//            gson.fromJson(json, type)
//        }else{
//            null
//        }
//    }

    fun saveTodayForecast(responseOneCall: ResponseOneCall) {
        val prefsEditor = mSharedPref!!.edit()
        val gson = Gson()
        val json: String = gson.toJson(responseOneCall)
        prefsEditor.putString("today_forecast", json)
        prefsEditor.apply()
    }

    fun getTodayForecast(): ResponseOneCall? {
        val gson = Gson()
        val json: String? = mSharedPref!!.getString("today_forecast", null)
        return if (json != null) {
            val type: Type = object : TypeToken<ResponseOneCall>() {}.type
            gson.fromJson(json, type)
        } else {
            null
        }

    }
    fun saveNextWeek(responseOneCall: ResponseNextWeek) {
        val prefsEditor = mSharedPref!!.edit()
        val gson = Gson()
        val json: String = gson.toJson(responseOneCall)
        prefsEditor.putString("next_week", json)
        prefsEditor.apply()
    }

    fun getNextWeek():ResponseNextWeek? {
        val gson = Gson()
        val json: String? = mSharedPref!!.getString("next_week", null)
        return if (json != null) {
            val type: Type = object : TypeToken<ResponseNextWeek>() {}.type
            gson.fromJson(json, type)
        }else{
            null
        }

    }

    fun saveAddress(address: Address) {
        val prefsEditor = mSharedPref!!.edit()
        val gson = Gson()
        val json: String = gson.toJson(address)
        prefsEditor.putString("address", json)
        prefsEditor.apply()
    }

    fun getAddress():Address? {
        val gson = Gson()
        val json: String? = mSharedPref!!.getString("address", null)
        return if (json != null) {
            val type: Type = object : TypeToken<Address>() {}.type
            gson.fromJson(json, type)
        }else{
            null
        }

    }


    fun saveApiKey(apiKey: String) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putString("api_key", apiKey)
        prefsEditor.apply()
    }

    fun getApiKey(): String? {
        return mSharedPref!!.getString("api_key", null)
    }

    fun saveToken(token: String) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putString("token", token)
        prefsEditor.apply()
    }

    fun getToken(): String? {
        return mSharedPref!!.getString("token", null)
    }


    fun saveIsLogin(isLogin: Boolean) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putBoolean("is_login_key", isLogin)
        prefsEditor.apply()
    }

    fun isLogin(): Boolean {
        return mSharedPref!!.getBoolean("is_login_key", false)
    }


    fun saveIsDayMode(isDayMode: Boolean) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putBoolean("is_day_mode", isDayMode)
        prefsEditor.apply()
    }

    fun isDayMode(): Boolean {
        return mSharedPref!!.getBoolean("is_day_mode", true)
    }


    /**
     * On boarding show or not that will define
     * */
    fun agree() {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putBoolean("on_boarding", true)
        prefsEditor.apply()
    }

    fun isAgree(): Boolean {
        return mSharedPref!!.getBoolean("on_boarding", false)
    }


    fun adType(): String? {
        return mSharedPref!!.getString("add_type", null)
    }

    fun bannerId(): String? {
        return mSharedPref!!.getString("banner_id", null)
    }

    fun bannerLink(): String? {
        return mSharedPref!!.getString("banner_link", null)
    }

    fun bannerImage(): String? {
        return mSharedPref!!.getString("banner_image", null)
    }

    fun imageUrl(): String? {
        return mSharedPref!!.getString("image_url", null)
    }

    fun interstitialId(): String? {
        return mSharedPref!!.getString("interstitial_id", null)
    }

    fun interstitialImage(): String? {
        return mSharedPref!!.getString("interstitial_image", null)
    }

    fun interstitialLink(): String? {
        return mSharedPref!!.getString("interstitial_link", null)
    }

    fun interstitialClick(): Int {
        return mSharedPref!!.getInt("interstitial_click", 0)
    }

    fun nativeId(): String? {
        return mSharedPref!!.getString("native_id", null)
    }

    fun nativeLink(): String? {
        return mSharedPref!!.getString("native_link", null)
    }

    fun nativeImage(): String? {
        return mSharedPref!!.getString("native_image", null)
    }

    fun nativePerImage(): Int {
        return mSharedPref!!.getInt("native_per_image", 0)
    }


    fun startAppId(): String? {
        return mSharedPref!!.getString("start_app_id", null)
    }


    /**
     * For interstitial ads we have to count user click that's
     * why we saved click count
     * */
    fun clickCount(makeZero: Boolean = false) {
        val previewsValue = getClickCount() + 1
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putInt("click_count", if (makeZero) 0 else previewsValue)
        prefsEditor.apply()
    }

    fun getClickCount(): Int {
        return mSharedPref!!.getInt("click_count", 0)
    }

    /**
     * One Signal keys
     * */

    fun saveOneSignalId(id: String) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putString("one_signal", id)
        prefsEditor.apply()
    }

    fun getOneSignalId(): String? {
        return mSharedPref!!.getString("one_signal", null)
    }

/*
    fun setNotification(isEnable: Boolean) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putBoolean("notification",isEnable)
        prefsEditor.apply()
    }
    fun getNotification():Boolean{
        return mSharedPref!!.getBoolean("notification",true)
    }*/


    /**
     * Data save for subscription check
     * */
    fun saveProductId(id: String) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putString("subscription_product_id", id)
        prefsEditor.apply()
    }

    fun getProductId(): String? {
        return mSharedPref!!.getString("subscription_product_id", null)
    }

    fun setIsPremium(isPremium: Boolean) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putBoolean("is_premium", isPremium)
        prefsEditor.apply()
    }

    fun isPremium(): Boolean {
        return mSharedPref!!.getBoolean("is_premium", false)
    }

    fun saveStartDate(startDate: String) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putString("start_date", startDate)
        prefsEditor.apply()
    }

    fun getStartDate(): String? {
        return mSharedPref!!.getString("start_date", null)
    }

    fun saveExpireDate(endDate: String) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putString("expire_date", endDate)
        prefsEditor.apply()
    }

    fun getExpireDate(): String? {
        return mSharedPref!!.getString("expire_date", null)
    }

    fun saveSubDes(description: String) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putString("sub_description", description)
        prefsEditor.apply()
    }

    fun getSubDesc(): String? {
        return mSharedPref!!.getString("sub_description", null)
    }

    fun saveSubName(name: String) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putString("sub_name", name)
        prefsEditor.apply()
    }

    fun getSubName(): String? {
        return mSharedPref!!.getString("sub_name", null)
    }


    fun saveSubDuration(name: String) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putString("sub_duration", name)
        prefsEditor.apply()
    }

    fun getSubDuration(): String? {
        return mSharedPref!!.getString("sub_duration", null)
    }

    fun saveSubPrice(name: String) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putString("sub_price", name)
        prefsEditor.apply()
    }

    fun getSubPrice(): String? {
        return mSharedPref!!.getString("sub_price", null)
    }


    /**
     * Temperature unit
     * */
    const val TEMPERATURE_CELSIUS = "celsius"
    const val TEMPERATURE_FAHRENHEIT = "fahrenheit"
    fun setTemperatureUnit(name: String) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putString("temperature_unit", name)
        prefsEditor.apply()
    }

    fun getTemperatureUnit(): String {
        return mSharedPref!!.getString("temperature_unit", TEMPERATURE_CELSIUS)!!
    }

    /**
     * Wind Format
     * */
    const val WIND_MS = "m/s"
    const val WIND_KMH = "km/h"
    const val WIND_MPH = "mph"
    fun setWindFormat(wind: String) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putString("wind_format", wind)
        prefsEditor.apply()
    }

    fun getWindFormat(): String {
        return mSharedPref!!.getString("wind_format", WIND_MS)!!
    }

    /**
     * Time Format
     * */
    const val TIME_24 = "time24"
    const val TIME_12 = "time12"
    fun setTimeFormat(time: String) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putString("time_format", time)
        prefsEditor.apply()
    }

    fun getTimeFormat(): String {
        return mSharedPref!!.getString("time_format", TIME_24)!!
    }
}