package ru.weather.settings.models

import ru.weather.settings.SettingsContract


internal data class StateEventEffectModel(
    val state: SettingsContract.State,
    val event: (event: SettingsContract.Event) -> Unit,
    val effect: SettingsContract.Effect? = null,
)
