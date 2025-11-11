package ru.weather.core.navigation.model

data class NavigationCommand(val action: NavigationAction, val destination: String? = null)
