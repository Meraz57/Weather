package xyz.teamprojectx.weather.data.response.nextWeek


import com.google.gson.annotations.SerializedName

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