package ru.weather.data.datasource.remotesource

import ru.weather.data.remoteservice.api.entity.response.weather.WeatherDataResponse

interface RemoteDataSource {
    suspend fun getWeatherData(query: String, days: Int, language: String): WeatherDataResponse
}