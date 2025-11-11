package ru.weather.simpleweatherapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.weather.domain.usecase.GetWeatherUseCase
import ru.weather.simpleweatherapp.services.notification.WeatherNotificationManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NotificationModule {
    @Provides
    @Singleton
    fun provideWeatherNotificationManager(
        @ApplicationContext context: Context,
        getWeatherUseCase: GetWeatherUseCase
    ): WeatherNotificationManager {
        return WeatherNotificationManager(context, getWeatherUseCase)
    }
}