package com.example.weather.network

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
interface API {

 @GET("blog/get-all")
 fun newsPost():Call<List<Data>>


}