package com.example.weather.network

import android.content.Context
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitOpenWeatherClient {

  /*  companion object {
        private val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiInterfaceOW: API = retrofit.create(API::class.java)
    }*/



  companion object {

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
              .baseUrl(Const.OPEN_WEATHER_BASE_URL)
              .client(okHttpClint)
              .build()
              .create(API::class.java)

      }
  }

}
//
//https://api.openweathermap.org/api/data/2.5/weather
//https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API key}
//
