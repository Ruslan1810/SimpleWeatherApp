package ru.weather.settings.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.weather.settings.SettingsScreen
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsFeatureApi @Inject constructor() : SettingsApi {
    override val baseRoute = SettingsDestinations.Common.SETTINGS_ROOT_ROUTE

    override fun registerGraph(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.composable(route = baseRoute) {
            SettingsScreen()
        }

        navGraphBuilder.composable(route = SettingsDestinations.Common.Profile()) {

        }

        navGraphBuilder.composable(route = SettingsDestinations.Common.Notifications()) {

        }

        navGraphBuilder.composable(route = SettingsDestinations.Common.Theme()) {
//            ThemeNavGraph()
        }

        navGraphBuilder.composable(route = SettingsDestinations.Common.About()) {
//            AboutNavGraph()
        }
    }
}

