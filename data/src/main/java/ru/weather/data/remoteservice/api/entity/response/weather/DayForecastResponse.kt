package ru.weather.data.remoteservice.api.entity.response.weather

import com.google.gson.annotations.SerializedName

data class DayForecastResponse(
    @SerializedName("maxtemp_c") val maxTempC: Double?,
    @SerializedName("mintemp_c") val minTempC: Double?,
    @SerializedName("avgtemp_c") val avgTempC: Double?,
    @SerializedName("condition") val condition: WeatherConditionResponse?,
    @SerializedName("maxwind_kph") val maxWindKph: Double?,
    @SerializedName("totalprecip_mm") val totalPrecipMm: Double?,
    @SerializedName("avghumidity") val avgHumidity: Int?,
    @SerializedName("daily_chance_of_rain") val dailyChanceOfRain: Int?
)
