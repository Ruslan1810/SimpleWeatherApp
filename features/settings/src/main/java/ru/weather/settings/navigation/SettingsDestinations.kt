package ru.weather.settings.navigation

import ru.weather.core.navigation.NavigationComposeEntry

sealed class SettingsDestinations(override val baseRoute: String) : NavigationComposeEntry {
    operator fun invoke() = getComposableRoute()

    sealed class Common(override val baseRoute: String) : SettingsDestinations("$SETTINGS_ROOT_ROUTE/$baseRoute") {
        companion object {
            const val SETTINGS_ROOT_ROUTE = "settings_section"
        }

        data object Main : Common("main")
        data object Profile : Common("profile")
        data object Notifications : Common("notifications")
        data object About : Common("about")
        data object Theme : Common("theme")
    }
}