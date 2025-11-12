package ru.weather.data.datasource.locationsource

import android.location.Location

interface GeoLocationManager {
    suspend fun getCurrentLocation(): Location
}