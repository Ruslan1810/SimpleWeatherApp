package ru.weather.domain.models

data class WeatherConditionModel(
    val text: String,
    val icon: String,
    val code: Int
){
    companion object {
        fun default() = WeatherConditionModel(
            text = "",
            icon = "",
            code = 0,
        )
    }
}


