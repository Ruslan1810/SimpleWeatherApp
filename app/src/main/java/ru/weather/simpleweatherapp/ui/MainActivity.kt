package ru.weather.simpleweatherapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.weather.core.navigation.FeatureApi
import ru.weather.core.navigation.NavigationManager
import ru.weather.simpleweatherapp.services.notification.NotificationPermissionManager
import ru.weather.simpleweatherapp.services.notification.WeatherNotificationManager
import ru.weather.simpleweatherapp.ui.theme.TestWeatherTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationManager: NavigationManager

    @Inject
    lateinit var featureProviders: Set<@JvmSuppressWildcards FeatureApi>

    @Inject
    lateinit var notificationManager: WeatherNotificationManager
    private lateinit var permissionManager: NotificationPermissionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        permissionManager = NotificationPermissionManager(this)

        permissionManager.checkAndRequestNotificationPermission { isGranted ->
            if (isGranted) {
                notificationManager.startPeriodicNotifications()
            }
        }

        setContent {
            TestWeatherTheme {
                val navController = rememberNavController()
                MainScreen(
                    navController = navController,
                    featureProviders = featureProviders,
                    navigationManager = navigationManager
                )
            }
        }
    }
}