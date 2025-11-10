package ru.weather.domain.models

data class DayForecastModel(
    val maxTempC: Double,
    val minTempC: Double,
    val avgTempC: Double,
    val condition: WeatherConditionModel,
    val maxWindKph: Double,
    val totalPrecipMm: Double,
    val avgHumidity: Int,
    val dailyChanceOfRain: Int
)
