package ru.weather.data.remoteservice.api.entity.response.weather

import com.google.gson.annotations.SerializedName

data class ForecastDayResponse(
    @SerializedName("date") val date: String?,
    @SerializedName("day") val day: DayForecastResponse?,
    @SerializedName("hour") val hour: List<HourForecastResponse?>?
)
