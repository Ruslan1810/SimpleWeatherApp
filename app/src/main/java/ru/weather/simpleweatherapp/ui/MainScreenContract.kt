package ru.weather.simpleweatherapp.ui

import ru.weather.core.base.ViewEvent
import ru.weather.core.base.ViewSideEffect
import ru.weather.core.base.ViewState
import ru.weather.core.comon_ui.model.ErrorType
import ru.weather.domain.models.CurrentWeatherModel
import ru.weather.domain.models.ForecastModel
import ru.weather.domain.models.LocationModel
import ru.weather.domain.models.WeatherDataModel
import ru.weather.simpleweatherapp.models.LocationData

class MainScreenContract {

    data class State(
        val loadingResult: LoadingResult,
        val data: WeatherDataModel,
        val errorType: ErrorType,
        val locationState: LocationState,
        val locationData: LocationData
    ) : ViewState {

        companion object {
            fun default() = State(
                loadingResult = LoadingResult.LOADING,
                data = WeatherDataModel(
                    LocationModel.default(),
                    CurrentWeatherModel.default(),
                    ForecastModel.default()
                ),
                errorType = ErrorType.UNKNOWN,
                locationState = LocationState.DEFAULT,
                locationData = LocationData.MOSCOW
            )
        }
    }

    sealed class Event : ViewEvent {
        data class LoadDataSuccess(val data: WeatherDataModel) : Event()
        data class LoadDataError(val errorType: ErrorType): Event()
        data object LoadingData: Event()
        data object RetryLoading : Event()

        data object RequestLocation : Event()
        data class LocationDetected(val latitude: Double, val longitude: Double) : Event()
        data object LocationError : Event()
    }

    sealed class Effect : ViewSideEffect {
        data class RequestLocationPermission(val onGranted: () -> Unit) : Effect()
        data class ShowLocationError(val message: String) : Effect()

        data class ShowToast(val message: String): Effect()
    }
}

enum class LoadingResult{
    LOADING,
    SUCCESS,
    ERROR
}


enum class LocationState {
    DEFAULT,
    DETECTING,
    DETECTED,
    ERROR
}