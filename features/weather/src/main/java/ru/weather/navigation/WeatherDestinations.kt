package ru.weather.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import ru.weather.core.navigation.NavigationComposeEntry

sealed class WeatherDestinations(override val baseRoute: String) : NavigationComposeEntry {
    operator fun invoke() = getComposableRoute()

    sealed class Common(override val baseRoute: String) :
        WeatherDestinations("$WEATHER_ROOT_ROUTE/$baseRoute") {
        companion object {
            const val WEATHER_ROOT_ROUTE = "weather_section"
        }

        data object Main : Common("main")
        data object Detail : Common("detail") {
            override val arguments = listOf(
                navArgument("cityId") { type = NavType.StringType }
            )

            fun createRoute(cityId: String) = getNavigationRoute(cityId)
        }

        data object Map : Common("map")
    }
}