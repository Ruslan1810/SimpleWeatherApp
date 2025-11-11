package ru.weather.cities.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import ru.weather.core.navigation.NavigationComposeEntry

sealed class CitiesDestinations(override val baseRoute: String) : NavigationComposeEntry {
    operator fun invoke() = getComposableRoute()

    sealed class Common(override val baseRoute: String) : CitiesDestinations("$CITIES_ROOT_ROUTE/$baseRoute") {
        companion object {
            const val CITIES_ROOT_ROUTE = "cities_section"
        }

        object Main : Common("main")
        object Search : Common("search")
        object AddCity : Common("add_city")
        object CityDetail : Common("city_detail") {
            override val arguments = listOf(
                navArgument("cityId") { type = NavType.StringType }
            )
            fun createRoute(cityId: String) = getNavigationRoute(cityId)
        }
    }
}