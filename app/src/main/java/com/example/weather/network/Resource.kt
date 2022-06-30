package com.example.weather.network

import xyz.teamprojectx.weather.data.response.errorResponse.ResponseError

sealed class Resource<T>(
    val data:T? = null,
    val message:String?=null,
    val error: ResponseError? =null
){
    class Success<T>(data: T?): Resource<T>(data)
    class Error<T>(message: String?, error: ResponseError? =null): Resource<T>(
        message =message,
        error =error)
    class Loading<T>: Resource<T>()
}
