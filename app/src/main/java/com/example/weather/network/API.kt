package com.example.weather.network
import com.example.weather.dataclass.data.ResponseNewsData
import com.example.weather.dataclass.data.currentweather.CurrentWeather
import com.example.weather.dataclass.data.currentweather.Main
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface API {

    @GET("blog/get-all")
    fun newsPost(): Call<ResponseNewsData>

    @Headers("Accept:application/json")
    @GET("data/2.5/weather")
     fun weather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") apiKey: String
    ): Call<CurrentWeather>

}