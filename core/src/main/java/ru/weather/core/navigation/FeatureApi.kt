package ru.weather.core.navigation

import androidx.navigation.NavGraphBuilder

interface FeatureApi {
    fun registerGraph(navGraphBuilder: NavGraphBuilder)
}
