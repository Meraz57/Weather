package com.example.weather.network


import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import xyz.teamprojectx.weather.data.response.errorResponse.ResponseError

abstract class SafeApiCall {

    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {


        val response = call.invoke()

        if (response.isSuccessful) {
            return response.body()!!
        } else {

            try {
                 val  error = JSONObject(response.errorBody()!!.charStream().readText())
                val errorModel =
                    Gson().fromJson(error.toString(), ResponseError::class.java)

                if (errorModel != null) {
                    throw ApiException(errorModel.message?:errorModel.status?:"",errorModel)
                }else{
                    throw ApiException("error")
                }


            } catch (e: JSONException) {
                throw ApiException("Something is wrong !")
            }


        }


    }


}