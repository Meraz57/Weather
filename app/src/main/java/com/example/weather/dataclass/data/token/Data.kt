package com.example.weather.dataclass.data.token


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("token")
    val token: String?
)