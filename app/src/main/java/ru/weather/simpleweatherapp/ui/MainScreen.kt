package ru.weather.simpleweatherapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.weather.cities.navigation.CitiesDestinations
import ru.weather.core.navigation.FeatureApi
import ru.weather.core.navigation.NavigationManager
import ru.weather.core.navigation.model.NavigationAction
import ru.weather.navigation.WeatherDestinations
import ru.weather.settings.navigation.SettingsDestinations

@Composable
fun MainScreen(
    navController: NavHostController,
    featureProviders: Set<FeatureApi>,
    navigationManager: NavigationManager
) {
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination

    // Обработка навигационных команд из NavigationManager
    LaunchedEffect(Unit) {
        navigationManager.commands.collect { command ->
            when (command.action) {
                NavigationAction.FORWARD -> command.destination?.let { navController.navigate(it) }
                NavigationAction.BACK -> navController.popBackStack()
                NavigationAction.REPLACE -> command.destination?.let { route ->
                    navController.navigate(route) {
                        popUpTo(0)
                    }
                }
                NavigationAction.REPLACE_CURRENT -> command.destination?.let { route ->
                    navController.navigate(route) {
                        popUpTo(navController.currentBackStackEntry?.destination?.route ?: return@navigate)
                    }
                }
                else -> {  }
            }
        }
    }

    Scaffold(
        bottomBar = {
            val bottomBarRoutes = listOf(
                WeatherDestinations.Common.WEATHER_ROOT_ROUTE,
                CitiesDestinations.Common.CITIES_ROOT_ROUTE,
                SettingsDestinations.Common.SETTINGS_ROOT_ROUTE
            )

            if (currentDestination?.route in bottomBarRoutes) {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentDestination?.route == WeatherDestinations.Common.WEATHER_ROOT_ROUTE,
                        onClick = {
                            navController.navigate(WeatherDestinations.Common.WEATHER_ROOT_ROUTE) {
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                        label = { Text("Погода") }
                    )
                    NavigationBarItem(
                        selected = currentDestination?.route == CitiesDestinations.Common.CITIES_ROOT_ROUTE,
                        onClick = {
                            navController.navigate(CitiesDestinations.Common.CITIES_ROOT_ROUTE) {
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(Icons.Default.Home, contentDescription = null) },
                        label = { Text("Города") }
                    )
                    NavigationBarItem(
                        selected = currentDestination?.route == SettingsDestinations.Common.SETTINGS_ROOT_ROUTE,
                        onClick = {
                            navController.navigate(SettingsDestinations.Common.SETTINGS_ROOT_ROUTE) {
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                        label = { Text("Настройки") }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = WeatherDestinations.Common.WEATHER_ROOT_ROUTE,
            modifier = Modifier.padding(paddingValues)
        ) {
            featureProviders.forEach { feature ->
                feature.registerGraph(this)
            }
        }
    }
}