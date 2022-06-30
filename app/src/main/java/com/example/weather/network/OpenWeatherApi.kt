package com.example.weather.network

import android.content.Context
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import xyz.teamprojectx.weather.data.response.location.ResponseLocation
import xyz.teamprojectx.weather.data.response.nextWeek.ResponseNextWeek
import xyz.teamprojectx.weather.data.response.todayForecast.ResponseOneCall
import xyz.teamprojectx.weather.data.response.weather.ResponseWeather
import xyz.teamprojectx.weather.util.Const
import java.util.concurrent.TimeUnit

interface OpenWeatherApi {


    @GET("data/2.5/weather")
    suspend fun weather(
        @Query("lat")lat:String,
        @Query("lon")lon:String,
        @Query("units")units:String,
        @Query("appid")apiKey:String ,
        @Query("timezone")timezone:String= "UTC"
    ):Response<ResponseWeather>

    @GET("geo/1.0/direct")
    suspend fun searchLocation(
        @Query("q")query:String,
        @Query("limit")limit:Int = 5,
        @Query("appid")apiKey:String
    ):Response<ResponseLocation>

    @GET("data/2.5/onecall")
    suspend fun todayForecast(
        @Query("lat")lat:String,
        @Query("lon")lon:String,
        @Query("units")units:String,
        @Query("exclude")exclude:String="current,minutely,daily,alerts",
        @Query("appid")apiKey:String
    ):Response<ResponseOneCall>

    @GET("data/2.5/forecast")
    suspend fun nextWeek(
        @Query("lat")lat:String,
        @Query("lon")lon:String,
        @Query("units")units:String,
        @Query("appid")apiKey:String
    ):Response<ResponseNextWeek>


    companion object {

        operator fun invoke(context: Context): OpenWeatherApi {


            /**
             * This is for http data logging
             * */
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)


            val okHttpClint = OkHttpClient.Builder()
                .addInterceptor(InternetConnectionInterceptor(context))
                .addInterceptor(logging)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(Interceptor { chain ->
                    val builder = chain.request().newBuilder().apply {
                        header("Accept", "application/json")
                        header("Content-Type", "application/json")
                    }
                    return@Interceptor chain.proceed(builder.build())
                })
                .build()

            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Const.OPEN_WEATHER_BASE_URL)
                .client(okHttpClint)
                .build()
                .create(OpenWeatherApi::class.java)

        }
    }
}