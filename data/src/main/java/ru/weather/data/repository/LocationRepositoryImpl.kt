package ru.weather.data.repository

import ru.weather.data.GeoLocationManager
import ru.weather.domain.models.LocationResult
import ru.weather.domain.repository.LocationRepository
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationManager: GeoLocationManager
) : LocationRepository {

    override suspend fun getCurrentLocation(): LocationResult {
        return try {
            val location = locationManager.getCurrentLocation()
            LocationResult.Success(
                latitude = location.latitude,
                longitude = location.longitude
            )
        } catch (e: SecurityException) {
            LocationResult.PermissionRequired
        } catch (e: Exception) {
            LocationResult.Error(e.message ?: "Unknown error")
        }
    }
}