package ru.weather.domain.models

data class CurrentWeatherModel(
    val tempC: Double,
    val tempF: Double,
    val isDay: Int,
    val condition: WeatherConditionModel,
    val windKph: Double,
    val pressureMb: Double,
    val humidity: Int,
    val feelslikeC: Double,
    val visKm: Double
){
    companion object {
        fun default() = CurrentWeatherModel(
            tempC = 0.0,
            tempF = 0.0,
            isDay = 0,
            condition = WeatherConditionModel.default(),
            windKph = 0.0,
            pressureMb = 0.0,
            humidity = 0,
            feelslikeC = 0.0,
            visKm = 0.0,
        )
    }
}
