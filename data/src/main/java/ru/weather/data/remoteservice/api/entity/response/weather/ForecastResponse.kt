package ru.weather.data.remoteservice.api.entity.response.weather

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("forecastday") val forecastDays: List<ForecastDayResponse?>?
)
