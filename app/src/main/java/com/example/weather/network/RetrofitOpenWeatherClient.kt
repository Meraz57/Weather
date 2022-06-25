package com.example.weather.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitOpenWeatherClient {

    companion object {
        private val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiInterfaceOW: API = retrofit.create(API::class.java)
    }

}
//
//https://api.openweathermap.org/api/data/2.5/weather
//https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API key}
//
