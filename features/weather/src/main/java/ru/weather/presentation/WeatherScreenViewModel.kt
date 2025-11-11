package ru.weather.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.weather.core.base.BaseViewModel
import ru.weather.core.comon_ui.model.ErrorType
import ru.weather.core.navigation.NavigationManager
import ru.weather.domain.models.LocationResult
import ru.weather.domain.usecase.GetLocationUseCase
import ru.weather.domain.usecase.GetWeatherUseCase
import ru.weather.models.LocationData
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class WeatherScreenViewModel @Inject constructor(
    private val weatherUseCase: GetWeatherUseCase,
    private val locationUseCase: GetLocationUseCase,
    private val navigationManager: NavigationManager
) : BaseViewModel<WeatherScreenContract.Event, WeatherScreenContract.State, WeatherScreenContract.Effect>() {

    init {
        getWeatherData()
    }

    private fun getWeatherData(locationData: LocationData = currentState.locationData) {
        viewModelScope.launch(Dispatchers.IO) {
            setEvent(event = WeatherScreenContract.Event.LoadingData)

            /** По условию задания должент быть прогресбар,
             *  добавил задержку чтобы он был виден если быстрый интернет */
            delay(2000)
            weatherUseCase.getWeatherData(
                latitude = locationData.latitude,
                longitude = locationData.longitude,
                days = locationData.days,
                language = locationData.language
            )
                .onSuccess {
                    setEvent(event = WeatherScreenContract.Event.LoadDataSuccess(data = it))
                }.onFailure {
                    val errorType = when (it) {
                        is IOException -> ErrorType.NETWORK
//                        is HttpException -> ErrorType.SERVER
                        else -> ErrorType.UNKNOWN
                    }
                    setEvent(
                        event = WeatherScreenContract.Event.LoadDataError(errorType = errorType)
                    )
                }
        }
    }

    override fun setInitialState() = WeatherScreenContract.State.default()

    override fun handleEvents(event: WeatherScreenContract.Event) {
        when (event) {
            is WeatherScreenContract.Event.LoadDataSuccess -> setState {
                copy(loadingResult = LoadingResult.SUCCESS, data = event.data)
            }

            is WeatherScreenContract.Event.LoadDataError -> setState {
                copy(loadingResult = LoadingResult.ERROR, errorType = event.errorType)
            }

            is WeatherScreenContract.Event.LoadingData -> setState {
                copy(loadingResult = LoadingResult.LOADING)
            }

            is WeatherScreenContract.Event.RetryLoading -> {
                getWeatherData(locationData = currentState.locationData)
            }

            is WeatherScreenContract.Event.RequestLocation -> {
                requestLocation()
            }

            is WeatherScreenContract.Event.LocationDetected -> {
                setState {
                    copy(
                        locationState = LocationState.DETECTED,
                        locationData = LocationData(event.latitude, event.longitude)
                    )
                }
                getWeatherData(locationData = currentState.locationData)
                setEffect {
                    WeatherScreenContract.Effect.ShowToast("Местоположение определено!")
                }
            }

            is WeatherScreenContract.Event.LocationError -> {
                setState {
                    copy(locationState = LocationState.ERROR)
                }
                setEffect {
                    WeatherScreenContract.Effect.ShowLocationError("Не удалось определить местоположение")
                }
            }
        }
    }

    private fun requestLocation() {
        setState {
            copy(locationState = LocationState.DETECTING)
        }

        viewModelScope.launch {
            when (val result = locationUseCase.getLocationUseCase()) {
                is LocationResult.Success -> {
                    setEvent(
                        WeatherScreenContract.Event.LocationDetected(
                            latitude = result.latitude,
                            longitude = result.longitude
                        )
                    )
                }

                is LocationResult.PermissionRequired -> {
                    setEffect {
                        WeatherScreenContract.Effect.RequestLocationPermission {
                            requestLocation()
                        }
                    }
                }

                is LocationResult.Error -> {
                    setEvent(WeatherScreenContract.Event.LocationError)
                }
            }
        }
    }
}