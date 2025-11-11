package ru.weather.cities.navigation

import ru.weather.core.navigation.FeatureApi

interface CitiesApi : FeatureApi {
    val baseRoute: String
}