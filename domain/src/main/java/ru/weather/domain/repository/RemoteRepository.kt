package ru.weather.domain.repository

import ru.weather.domain.models.WeatherDataModel

interface RemoteRepository {
    suspend fun getWeatherData(
        latitude: Double,
        longitude: Double,
        days: Int,
        language: String
    ): Result<WeatherDataModel>
}