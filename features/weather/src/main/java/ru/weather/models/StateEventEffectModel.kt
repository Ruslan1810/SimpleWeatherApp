package ru.weather.models

import ru.weather.presentation.WeatherScreenContract

internal data class StateEventEffectModel(
    val state: WeatherScreenContract.State,
    val event: (event: WeatherScreenContract.Event) -> Unit,
    val effect: WeatherScreenContract.Effect? = null,
)
