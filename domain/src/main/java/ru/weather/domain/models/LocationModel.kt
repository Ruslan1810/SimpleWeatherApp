package ru.weather.domain.models

data class LocationModel(
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val localtime: String
) {
    companion object {
        fun default() = LocationModel(
            name = "Москва",
            region = "",
            country = "",
            lat = 0.0,
            lon = 0.0,
            localtime = "",
        )
    }
}