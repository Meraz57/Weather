package xyz.teamprojectx.weather.data.response.todayForecast


import com.example.weather.dataclass.data.todayForecast.Hourly
import com.google.gson.annotations.SerializedName

data class ResponseOneCall(
    @SerializedName("hourly")
    val hourly: List<Hourly>?,
    @SerializedName("lat")
    val lat: Double?,
    @SerializedName("lon")
    val lon: Double?,
    @SerializedName("timezone")
    val timezone: String?,
    @SerializedName("timezone_offset")
    val timezoneOffset: Int?
)