package xyz.teamprojectx.weather.data.response.errorResponse


import com.google.gson.annotations.SerializedName

data class ErrorData(
    @SerializedName("error")
    val error: String?,
    @SerializedName("field")
    val `field`: String?
)