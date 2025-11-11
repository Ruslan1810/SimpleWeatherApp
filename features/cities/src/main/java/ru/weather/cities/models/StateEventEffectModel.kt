package ru.weather.cities.models

import ru.weather.cities.CitiesContract


internal data class StateEventEffectModel(
    val state: CitiesContract.State,
    val event: (event: CitiesContract.Event) -> Unit,
    val effect: CitiesContract.Effect? = null,
)
