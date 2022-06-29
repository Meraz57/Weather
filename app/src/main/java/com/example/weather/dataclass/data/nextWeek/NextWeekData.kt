package com.example.weather.dataclass.data.nextWeek
import com.google.gson.annotations.SerializedName
import xyz.teamprojectx.weather.data.response.nextWeek.Main
import xyz.teamprojectx.weather.data.response.nextWeek.Weather

data class NextWeekData(
    @SerializedName("dt")
    val dt: Double?,
    @SerializedName("dt_txt")
    val dtTxt: String?,
    @SerializedName("main")
    val main: Main?,
    @SerializedName("weather")
    val weather: List<Weather>?
)