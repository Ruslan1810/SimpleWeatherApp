package ru.weather.core.navigation

import androidx.compose.runtime.staticCompositionLocalOf

val LocalNavigationManager = staticCompositionLocalOf<NavigationManager> {
    error("NavigationManager not provided")
}