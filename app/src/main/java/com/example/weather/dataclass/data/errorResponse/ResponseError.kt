package xyz.teamprojectx.weather.data.response.errorResponse


import com.google.gson.annotations.SerializedName
import xyz.teamprojectx.weather.data.response.errorResponse.ErrorData

data class ResponseError(
    @SerializedName("data")
    val `data`: List<ErrorData>?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status_code")
    val statusCode: Int?
)