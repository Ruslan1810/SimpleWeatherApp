package ru.weather.domain.models

data class ForecastDayModel(
    val date: String,
    val day: DayForecastModel,
    val hour: List<HourForecastModel>
)
