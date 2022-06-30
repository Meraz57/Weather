package com.example.weather.network


import xyz.teamprojectx.weather.data.response.errorResponse.ResponseError
import java.io.IOException


class ApiException(message: String, val errorData: ResponseError? = null):Exception(message)

class ApiExceptionNoInternet(message: String):IOException(message)