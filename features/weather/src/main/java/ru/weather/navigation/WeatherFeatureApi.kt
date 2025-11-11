package ru.weather.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.weather.presentation.WeatherScreen
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherFeatureApi @Inject constructor() : WeatherApi {
    override val baseRoute = WeatherDestinations.Common.WEATHER_ROOT_ROUTE

    override fun registerGraph(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.composable(route = baseRoute) {
            WeatherScreen()
        }

        navGraphBuilder.composable(
            route = WeatherDestinations.Common.Detail.getComposableRoute(),
            arguments = WeatherDestinations.Common.Detail.arguments
        ) {
//            WeatherDetailNavGraph()
        }

        navGraphBuilder.composable(route = WeatherDestinations.Common.Map()) {
//            WeatherMapNavGraph()
        }
    }
}

