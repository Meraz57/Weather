package xyz.teamprojectx.weather.data.response.nextWeek


import com.google.gson.annotations.SerializedName

data class ResponseNextWeek(
    @SerializedName("cnt")
    val cnt: Double?,
    @SerializedName("cod")
    val cod: String?,
    @SerializedName("list")
    val list: List<NextWeekData>?,
    @SerializedName("message")
    val message: Double?
)