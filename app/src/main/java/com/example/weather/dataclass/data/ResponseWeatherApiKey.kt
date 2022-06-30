package com.example.weather.dataclass.data


import com.google.gson.annotations.SerializedName

data class ResponseWeatherApiKey(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("status_code")
    val statusCode: Int?
) {
    data class Data(
        @SerializedName("api_key")
        val apiKey: String?,
        @SerializedName("id")
        val id: String?
    )
}