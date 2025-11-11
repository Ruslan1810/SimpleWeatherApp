package ru.weather.settings.navigation

import ru.weather.core.navigation.FeatureApi

interface SettingsApi : FeatureApi {
    val baseRoute: String
}