package com.example.weather.network

import android.content.Context
import com.example.weather.dataclass.data.ResponseNewsData
import com.example.weather.dataclass.data.ResponseOneSignal
import com.example.weather.dataclass.data.ResponseWeatherApiKey
import com.example.weather.util.Const
import com.google.android.gms.common.api.Api
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface API {

    @GET("blog/get-all")
    fun newsPost(): Call<ResponseNewsData>

    @GET("weather-api/show")
    suspend fun weatherApiKey(): Response<ResponseWeatherApiKey>

    @GET("notification/get-api")
    suspend fun oneSignal():Response<ResponseOneSignal>

    /*  @Headers("Accept:application/json")
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
      ): Call<ResponseNextWeek>*/


    companion object {
        private const val BASE_URL = "${Const.BASE_URL}api/"

        operator fun invoke(context: Context): API {


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
                .baseUrl(BASE_URL)
                .client(okHttpClint)
                .build()
                .create(API::class.java)
        }
    }
}