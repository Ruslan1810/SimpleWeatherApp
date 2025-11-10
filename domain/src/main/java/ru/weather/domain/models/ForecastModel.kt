package ru.weather.domain.models

data class ForecastModel(
    val forecastDays: List<ForecastDayModel>
){
    companion object {
        fun default() = ForecastModel(
            forecastDays = listOf()
        )
    }
}
