package com.example.weather.network
import com.example.weather.dataclass.data.ResponseNewsData
import com.example.weather.dataclass.data.currentweather.CurrentWeather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import com.example.weather.dataclass.data.nextWeek.ResponseNextWeek
import com.example.weather.dataclass.data.location.ResponseLocation
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

    @GET("geo/1.0/direct")
     fun searchLocation(
        @Query("q")query:String,
        @Query("limit")limit:Int = 5,
        @Query("appid")apiKey:String
    ):Call<ResponseLocation>

}