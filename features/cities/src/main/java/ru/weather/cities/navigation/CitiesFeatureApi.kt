package ru.weather.cities.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.weather.cities.CitiesScreen
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CitiesFeatureApi @Inject constructor() : CitiesApi {
    override val baseRoute = CitiesDestinations.Common.CITIES_ROOT_ROUTE

    override fun registerGraph(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.composable(route = baseRoute) {
            CitiesScreen()
        }

        navGraphBuilder.composable(route = CitiesDestinations.Common.Search()) {

        }

        navGraphBuilder.composable(route = CitiesDestinations.Common.AddCity()) {

        }

        navGraphBuilder.composable(
            route = CitiesDestinations.Common.CityDetail.getComposableRoute(),
            arguments = CitiesDestinations.Common.CityDetail.arguments
        ) {

        }
    }
}


