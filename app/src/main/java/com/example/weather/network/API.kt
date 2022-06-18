package com.example.weather.network

import com.example.weather.DataClass.ResponseAbout
import retrofit2.Response
import retrofit2.http.GET
interface API {
 @GET("setting/show")
 fun about(): Response<ResponseAbout>


}