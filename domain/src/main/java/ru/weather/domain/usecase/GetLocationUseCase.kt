package ru.weather.domain.usecase

import ru.weather.domain.models.LocationResult
import ru.weather.domain.repository.LocationRepository

class GetLocationUseCase(
    private val locationRepository: LocationRepository
) {
    suspend fun getLocationUseCase(): LocationResult {
        return locationRepository.getCurrentLocation()
    }
}

