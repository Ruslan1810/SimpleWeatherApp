package ru.weather.data

import android.location.Location

interface GeoLocationManager {
    suspend fun getCurrentLocation(): Location
}