package ru.weather.data.remoteservice.api.entity.response.weather

import com.google.gson.annotations.SerializedName

data class HourForecastResponse(
    @SerializedName("time") val time: String?,
    @SerializedName("temp_c") val tempC: Double?,
    @SerializedName("is_day") val isDay: Int?,
    @SerializedName("condition") val condition: WeatherConditionResponse?,
    @SerializedName("wind_kph") val windKph: Double?,
    @SerializedName("chance_of_rain") val chanceOfRain: Int?
)