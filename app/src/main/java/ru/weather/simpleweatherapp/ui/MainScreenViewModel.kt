package ru.weather.simpleweatherapp.ui

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.weather.core.base.BaseViewModel
import ru.weather.core.comon_ui.model.ErrorType
import ru.weather.domain.models.LocationResult
import ru.weather.domain.usecase.GetLocationUseCase
import ru.weather.domain.usecase.GetWeatherUseCase
import ru.weather.simpleweatherapp.models.LocationData
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val weatherUseCase: GetWeatherUseCase,
    private val locationUseCase: GetLocationUseCase
) : BaseViewModel<MainScreenContract.Event, MainScreenContract.State, MainScreenContract.Effect>() {

    init {
        getWeatherData()
    }

    private fun getWeatherData(locationData: LocationData = currentState.locationData) {

        viewModelScope.launch(Dispatchers.IO) {
            setEvent(event = MainScreenContract.Event.LoadingData)

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
                    setEvent(event = MainScreenContract.Event.LoadDataSuccess(data = it))
                }.onFailure {
                    val errorType = when (it) {
                        is IOException -> ErrorType.NETWORK
                        is HttpException -> ErrorType.SERVER
                        else -> ErrorType.UNKNOWN
                    }
                    setEvent(
                        event = MainScreenContract.Event.LoadDataError(errorType = errorType)
                    )
                }
        }
    }

    override fun setInitialState() = MainScreenContract.State.default()

    override fun handleEvents(event: MainScreenContract.Event) {
        when (event) {
            is MainScreenContract.Event.LoadDataSuccess -> setState {
                copy(loadingResult = LoadingResult.SUCCESS, data = event.data)
            }

            is MainScreenContract.Event.LoadDataError -> setState {
                copy(loadingResult = LoadingResult.ERROR, errorType = event.errorType)
            }

            is MainScreenContract.Event.LoadingData -> setState {
                copy(loadingResult = LoadingResult.LOADING)
            }

            is MainScreenContract.Event.RetryLoading -> {
                getWeatherData(locationData = currentState.locationData)
            }

            is MainScreenContract.Event.RequestLocation -> {
                requestLocation()
            }

            is MainScreenContract.Event.LocationDetected -> {
                setState {
                    copy(
                        locationState = LocationState.DETECTED,
                        locationData = LocationData(event.latitude, event.longitude)
                    )
                }
                getWeatherData(locationData = currentState.locationData)
                setEffect {
                    MainScreenContract.Effect.ShowToast("Местоположение определено!")
                }
            }

            is MainScreenContract.Event.LocationError -> {
                setState {
                    copy(locationState = LocationState.ERROR)
                }
                setEffect {
                    MainScreenContract.Effect.ShowLocationError("Не удалось определить местоположение")
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
                        MainScreenContract.Event.LocationDetected(
                            latitude = result.latitude,
                            longitude = result.longitude
                        )
                    )
                }

                is LocationResult.PermissionRequired -> {
                    setEffect {
                        MainScreenContract.Effect.RequestLocationPermission {
                            requestLocation()
                        }
                    }
                }

                is LocationResult.Error -> {
                    setEvent(MainScreenContract.Event.LocationError)
                }
            }
        }
    }
}