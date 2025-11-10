package ru.weather.data.remoteservice.api.entity.response.weather

import com.google.gson.annotations.SerializedName

data class WeatherConditionResponse(
    @SerializedName("text") val text: String?,
    @SerializedName("icon") val icon: String?,
    @SerializedName("code") val code: Int?
)
