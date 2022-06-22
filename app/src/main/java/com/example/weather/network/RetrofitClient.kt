package com.example.weather.network

import com.example.weather.Const
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    companion object{
       private val retrofit= Retrofit.Builder()
           .baseUrl(Const.BASE_URL)
           .addConverterFactory(GsonConverterFactory.create())
           .build()
        val apiInterface:API= retrofit.create(API::class.java)
    }

}