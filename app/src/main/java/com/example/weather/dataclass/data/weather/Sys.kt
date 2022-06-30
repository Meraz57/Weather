package xyz.teamprojectx.weather.data.response.weather


import com.google.gson.annotations.SerializedName

data class Sys(
    @SerializedName("country")
    val country: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("message")
    val message: Double?,
    @SerializedName("sunrise")
    val sunrise: Long?,
    @SerializedName("sunset")
    val sunset: Long?,
    @SerializedName("type")
    val type: Int?
)