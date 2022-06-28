package com.example.weather.network
import com.example.weather.dataclass.data.ResponseNewsData
import com.example.weather.dataclass.data.currentweather.CurrentWeather
import com.example.weather.dataclass.data.currentweather.Main
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import xyz.teamprojectx.weather.data.response.nextWeek.ResponseNextWeek
import xyz.teamprojectx.weather.data.response.todayForecast.ResponseOneCall

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


    @GET("data/2.5/onecall")
     fun todayForecast(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("exclude") exclude:String,
        @Query("appid") apiKey: String
    ): Call<ResponseOneCall>

    @GET("data/2.5/forecast")
     fun nextWeek(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") apiKey: String
    ): Call<ResponseNextWeek>
}