package com.example.weather.network

import com.example.weather.DataClass.ResponseAbout
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface API {

 @GET("setting/show")
suspend fun about(): Response<ResponseAbout>


}