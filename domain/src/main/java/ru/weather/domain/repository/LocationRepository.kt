package ru.weather.domain.repository

import ru.weather.domain.models.LocationResult

interface LocationRepository {
    suspend fun getCurrentLocation(): LocationResult
}