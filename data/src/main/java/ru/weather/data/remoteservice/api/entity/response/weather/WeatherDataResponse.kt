package ru.weather.data.remoteservice.api.entity.response.weather

import com.google.gson.annotations.SerializedName

data class WeatherDataResponse (
    @SerializedName("location") val location: LocationResponse?,
    @SerializedName("current") val current: CurrentWeatherResponse?,
    @SerializedName("forecast") val forecast: ForecastResponse?
)