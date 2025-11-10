package ru.weather.domain.models

sealed class LocationResult {
    data class Success(val latitude: Double, val longitude: Double) : LocationResult()
    data object PermissionRequired : LocationResult()
    data class Error(val message: String) : LocationResult()
}
