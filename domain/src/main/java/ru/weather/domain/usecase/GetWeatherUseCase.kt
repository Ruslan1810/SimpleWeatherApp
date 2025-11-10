package ru.weather.domain.usecase

import ru.weather.domain.models.WeatherDataModel
import ru.weather.domain.repository.RemoteRepository

class GetWeatherUseCase(private val repository: RemoteRepository) {
    suspend fun getWeatherData(
        latitude: Double,
        longitude: Double,
        days: Int,
        language: String
    ): Result<WeatherDataModel> = repository.getWeatherData(
        latitude = latitude,
        longitude = longitude,
        days = days,
        language = language
    )
}