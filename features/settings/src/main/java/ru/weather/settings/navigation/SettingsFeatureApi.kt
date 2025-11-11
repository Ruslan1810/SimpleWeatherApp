package ru.weather.settings.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.weather.settings.SettingsNavGraph
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsFeatureApi @Inject constructor() : SettingsApi {
    override val baseRoute = SettingsDestinations.Common.SETTINGS_ROOT_ROUTE

    override fun registerGraph(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.composable(route = baseRoute) {
            SettingsNavGraph()
        }

        navGraphBuilder.composable(route = SettingsDestinations.Common.Profile()) {
//            ProfileNavGraph()
        }

        navGraphBuilder.composable(route = SettingsDestinations.Common.Notifications()) {
//            NotificationsNavGraph()
        }

        navGraphBuilder.composable(route = SettingsDestinations.Common.Theme()) {
//            ThemeNavGraph()
        }

        navGraphBuilder.composable(route = SettingsDestinations.Common.About()) {
//            AboutNavGraph()
        }
    }
}

