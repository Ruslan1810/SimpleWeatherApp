package ru.weather.data.remoteservice.api.client

import retrofit2.http.GET
import retrofit2.http.Query
import ru.weather.data.remoteservice.api.entity.response.weather.WeatherDataResponse

interface ApiService {
    @GET("forecast.json")
    suspend fun getWeatherData(
        @Query("q") query: String,
        @Query("days") days: Int,
        @Query("lang") language: String
    ): WeatherDataResponse
}

