package ru.weather.data.datasource.remotesource

import ru.weather.data.remoteservice.api.client.ApiService
import ru.weather.data.remoteservice.api.entity.response.weather.WeatherDataResponse
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private var apiService: ApiService) :
    RemoteDataSource {

    override suspend fun getWeatherData(query: String, days: Int, language: String): WeatherDataResponse {
        return apiService.getWeatherData(query = query, days = days, language = language)
    }
}