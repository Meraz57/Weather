package com.example.weather.DataClass

import com.google.gson.annotations.SerializedName


data class ResponseAbout(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("status_code")
    val statusCode: Int?
) {
    data class Data(
        @SerializedName("app_version")
        val appVersion: String?,
        @SerializedName("cookies_policy")
        val cookiesPolicy: String?,
        @SerializedName("copyright")
        val copyright: String?,
        @SerializedName("created_at")
        val createdAt: String?,
        @SerializedName("description")
        val description: String?,
        @SerializedName("developed_by")
        val developedBy: String?,
        @SerializedName("facebook")
        val facebook: String?,
        @SerializedName("id")
        val id: String?,
        @SerializedName("image")
        val image: String?,
        @SerializedName("instagram")
        val instagram: String?,
        @SerializedName("mail_address")
        val mailAddress: String?,
        @SerializedName("privacy_policy")
        val privacyPolicy: String?,
        @SerializedName("system_name")
        val systemName: String?,
        @SerializedName("terms_policy")
        val termsPolicy: String?,
        @SerializedName("twitter")
        val twitter: String?,
        @SerializedName("update_app")
        val updateApp: String?,
        @SerializedName("updated_at")
        val updatedAt: String?,
        @SerializedName("youtube")
        val youtube: String?
    )
}