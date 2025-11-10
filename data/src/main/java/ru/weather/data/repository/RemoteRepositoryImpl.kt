package ru.weather.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.weather.data.datasource.remotesource.RemoteDataSource
import ru.weather.data.mapper.converting
import ru.weather.domain.models.WeatherDataModel
import ru.weather.domain.repository.RemoteRepository
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private var dataSource: RemoteDataSource
) : RemoteRepository {
    override suspend fun getWeatherData(
        latitude: Double,
        longitude: Double,
        days: Int,
        language: String
    ): Result<WeatherDataModel> = runCatching {
        withContext(Dispatchers.IO) {
            dataSource.getWeatherData(
                query = "$latitude,$longitude",
                days = days,
                language = language
            ).converting()
        }
    }
}