package ru.weather.simpleweatherapp.services.notification

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class WeatherUpdateWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted params: WorkerParameters,
    private val notificationManager: WeatherNotificationManager
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return try {
            notificationManager.showWeatherNotification()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}


