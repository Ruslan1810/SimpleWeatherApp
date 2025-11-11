package ru.weather.navigation

import ru.weather.core.navigation.FeatureApi

interface WeatherApi : FeatureApi {
    val baseRoute: String
}