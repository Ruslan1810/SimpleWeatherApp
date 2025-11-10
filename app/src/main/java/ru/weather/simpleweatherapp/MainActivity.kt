package ru.weather.simpleweatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.compose.LocalLifecycleOwner
import dagger.hilt.android.AndroidEntryPoint
import ru.weather.simpleweatherapp.services.notification.NotificationPermissionManager
import ru.weather.simpleweatherapp.services.notification.WeatherNotificationManager
import ru.weather.simpleweatherapp.ui.MainScreen
import ru.weather.simpleweatherapp.ui.theme.TestWeatherTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

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
                CompositionLocalProvider(
                    LocalLifecycleOwner provides this
                ) {
                    MainScreen()
                }
            }
        }
    }
}