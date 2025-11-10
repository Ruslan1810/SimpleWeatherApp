package ru.weather.data.remoteservice.api.entity.response.weather

import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    @SerializedName("temp_c") val tempC: Double?,
    @SerializedName("temp_f") val tempF: Double?,
    @SerializedName("is_day") val isDay: Int?,
    @SerializedName("condition") val condition: WeatherConditionResponse?,
    @SerializedName("wind_kph") val windKph: Double?,
    @SerializedName("pressure_mb") val pressureMb: Double?,
    @SerializedName("humidity") val humidity: Int?,
    @SerializedName("feelslike_c") val feelslikeC: Double?,
    @SerializedName("vis_km") val visKm: Double?
)
