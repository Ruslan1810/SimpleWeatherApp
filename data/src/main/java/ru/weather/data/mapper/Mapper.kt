package ru.weather.data.mapper

import ru.weather.data.remoteservice.api.entity.response.weather.CurrentWeatherResponse
import ru.weather.data.remoteservice.api.entity.response.weather.DayForecastResponse
import ru.weather.data.remoteservice.api.entity.response.weather.ForecastDayResponse
import ru.weather.data.remoteservice.api.entity.response.weather.ForecastResponse
import ru.weather.data.remoteservice.api.entity.response.weather.HourForecastResponse
import ru.weather.data.remoteservice.api.entity.response.weather.LocationResponse
import ru.weather.data.remoteservice.api.entity.response.weather.WeatherConditionResponse
import ru.weather.data.remoteservice.api.entity.response.weather.WeatherDataResponse
import ru.weather.domain.models.CurrentWeatherModel
import ru.weather.domain.models.DayForecastModel
import ru.weather.domain.models.ForecastDayModel
import ru.weather.domain.models.ForecastModel
import ru.weather.domain.models.HourForecastModel
import ru.weather.domain.models.LocationModel
import ru.weather.domain.models.WeatherConditionModel
import ru.weather.domain.models.WeatherDataModel


internal fun WeatherDataResponse.converting(): WeatherDataModel {
    return WeatherDataModel(
        location = convertLocation(this.location),
        current = convertCurrentWeather(this.current),
        forecast = convertForecast(this.forecast)
    )
}

internal fun convertLocation(location: LocationResponse?): LocationModel {
    return LocationModel(
        name = location?.name.orEmpty(),
        region = location?.region.orEmpty(),
        country = location?.country.orEmpty(),
        lat = location?.lat ?: 0.0,
        lon = location?.lon ?: 0.0,
        localtime = location?.localtime.orEmpty()
    )
}

internal fun convertCurrentWeather(current: CurrentWeatherResponse?): CurrentWeatherModel {
    return CurrentWeatherModel(
        tempC = current?.tempC ?: 0.0,
        tempF = current?.tempF ?: 0.0,
        isDay = current?.isDay ?: 0,
        condition = convertWeatherCondition(current?.condition),
        windKph = current?.windKph ?: 0.0,
        pressureMb = current?.pressureMb ?: 0.0,
        humidity = current?.humidity ?: 0,
        feelslikeC = current?.feelslikeC ?: 0.0,
        visKm = current?.visKm ?: 0.0
    )
}

internal fun convertWeatherCondition(condition: WeatherConditionResponse?): WeatherConditionModel {
    return WeatherConditionModel(
        text = condition?.text.orEmpty(),
        icon = condition?.icon.orEmpty(),
        code = condition?.code ?: 0
    )
}

internal fun convertForecast(forecast: ForecastResponse?): ForecastModel {
    return ForecastModel(
        forecastDays = convertForecastDays(forecast?.forecastDays)
    )
}

internal fun convertForecastDays(forecastDays: List<ForecastDayResponse?>?): List<ForecastDayModel> {
    return forecastDays?.mapNotNull { forecastDayResponse ->
        forecastDayResponse?.let { convertForecastDay(it) }
    } ?: emptyList()
}

internal  fun convertForecastDay(forecastDay: ForecastDayResponse): ForecastDayModel {
    return ForecastDayModel(
        date = forecastDay.date.orEmpty(),
        day = convertDayForecast(forecastDay.day),
        hour = convertHourForecasts(forecastDay.hour)
    )
}

internal fun convertDayForecast(day: DayForecastResponse?): DayForecastModel {
    return DayForecastModel(
        maxTempC = day?.maxTempC ?: 0.0,
        minTempC = day?.minTempC ?: 0.0,
        avgTempC = day?.avgTempC ?: 0.0,
        condition = convertWeatherCondition(day?.condition),
        maxWindKph = day?.maxWindKph ?: 0.0,
        totalPrecipMm = day?.totalPrecipMm ?: 0.0,
        avgHumidity = day?.avgHumidity ?: 0,
        dailyChanceOfRain = day?.dailyChanceOfRain ?: 0
    )
}

internal fun convertHourForecasts(hours: List<HourForecastResponse?>?): List<HourForecastModel> {
    return hours?.mapNotNull { hourResponse ->
        hourResponse?.let { convertHourForecast(it) }
    } ?: emptyList()
}

internal fun convertHourForecast(hour: HourForecastResponse): HourForecastModel {
    return HourForecastModel(
        time = hour.time.orEmpty(),
        tempC = hour.tempC ?: 0.0,
        isDay = hour.isDay ?: 0,
        condition = convertWeatherCondition(hour.condition),
        windKph = hour.windKph ?: 0.0,
        chanceOfRain = hour.chanceOfRain ?: 0
    )
}
