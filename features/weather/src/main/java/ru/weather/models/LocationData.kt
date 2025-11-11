package ru.weather.models


/** По умолчанию локация - Москва */
data class LocationData(
    val latitude: Double,
    val longitude: Double,
    val days: Int = 3,
    val language: String = "ru"
)
{
    companion object {
        val MOSCOW = LocationData(55.7569, 37.6151)
    }
}