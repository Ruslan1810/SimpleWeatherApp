package ru.weather.domain.models

data class HourForecastModel(
    val time: String,
    val tempC: Double,
    val isDay: Int,
    val condition: WeatherConditionModel,
    val windKph: Double,
    val chanceOfRain: Int
)