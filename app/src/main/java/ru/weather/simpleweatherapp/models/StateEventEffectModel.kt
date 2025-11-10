package ru.weather.simpleweatherapp.models

import ru.weather.simpleweatherapp.ui.MainScreenContract


internal data class StateEventEffectModel(
    val state: MainScreenContract.State,
    val event: (event: MainScreenContract.Event) -> Unit,
    val effect: MainScreenContract.Effect? = null,
)
