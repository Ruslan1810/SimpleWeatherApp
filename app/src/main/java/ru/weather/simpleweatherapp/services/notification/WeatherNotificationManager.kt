package ru.weather.simpleweatherapp.services.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.weather.domain.models.WeatherDataModel
import ru.weather.domain.usecase.GetWeatherUseCase
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherNotificationManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getWeatherUseCase: GetWeatherUseCase
) {
    companion object {
        private const val CHANNEL_ID = "weather_updates"
        private const val CHANNEL_NAME = "Погода"
        private const val CHANNEL_DESCRIPTION = "Уведомления о текущей погоде"
        private const val NOTIFICATION_ID = 1001
        private const val WORK_NAME = "weather_periodic_updates"
    }

    /** Запускает периодические уведомления каждый час */
    fun startPeriodicNotifications() {
        ensureNotificationChannel()

        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        val periodicWorkRequest = PeriodicWorkRequestBuilder<WeatherUpdateWorker>(
            repeatInterval = 1,
            repeatIntervalTimeUnit = TimeUnit.HOURS
        )
            .setInitialDelay(0, TimeUnit.SECONDS)
            .setConstraints(constraints)
            .build()

        val workManager = WorkManager.getInstance(context)

        workManager.enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.UPDATE,
            periodicWorkRequest
        )
    }

    /** Создает канал уведомлений если его нет */
    private fun ensureNotificationChannel() {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (notificationManager.getNotificationChannel(CHANNEL_ID) == null) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = CHANNEL_DESCRIPTION
                enableVibration(true)
                vibrationPattern = longArrayOf(100, 200, 100, 200)
            }

            notificationManager.createNotificationChannel(channel)
        }
    }

    /** Показывает уведомление с текущей погодой */
    suspend fun showWeatherNotification() = withContext(Dispatchers.IO) {
        try {
            if (!shouldShowNotification()) {
                return@withContext
            }

            val weatherData = getWeatherUseCase.getWeatherData(
                55.7569, 37.6151, 3, "ru"
            ).getOrNull() ?: return@withContext

            val notification = createNotification(weatherData)
            showNotificationSafely(notification)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /** Проверяет все условия для показа уведомления */
    private fun shouldShowNotification(): Boolean {
        return hasNotificationPermission() &&
                areSystemNotificationsEnabled()
    }

    /** Безопасный показ уведомления с явной проверкой */
    private fun showNotificationSafely(notification: Notification) {
        try {
            if (shouldShowNotification()) {
                NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
            }
        } catch (securityException: SecurityException) {
            securityException.printStackTrace()
        }
    }

    /** Проверяет, включены ли уведомления системно */
    private fun areSystemNotificationsEnabled(): Boolean {
        return NotificationManagerCompat.from(context).areNotificationsEnabled()
    }

    private fun createNotification(weatherData: WeatherDataModel): Notification {
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("🌤️ ${weatherData.location.name}")
            .setContentText("${weatherData.current.tempC}°C, ${weatherData.current.condition.text}")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(
                        """
                        🌡️ ${weatherData.current.tempC}°C 
                        💧 Влажность: ${weatherData.current.humidity}%
                        🌬️ Ветер: ${weatherData.current.windKph} км/ч
                        ${weatherData.current.condition.text}
                        """.trimIndent()
                    )
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
            .build()
    }

    /** Проверяет разрешение на показ уведомлений */
    private fun hasNotificationPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }
}