package ru.weather.domain.models

data class WeatherDataModel (
    val location: LocationModel,
    val current: CurrentWeatherModel,
    val forecast: ForecastModel
){
    companion object {
        fun default() = WeatherDataModel(
            location = LocationModel.default(),
            current = CurrentWeatherModel.default(),
            forecast = ForecastModel.default()
        )
    }
}