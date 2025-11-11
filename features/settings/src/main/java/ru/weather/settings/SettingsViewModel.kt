package ru.weather.settings

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.weather.core.base.BaseViewModel
import ru.weather.core.navigation.NavigationManager
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val navigationManager: NavigationManager
): BaseViewModel<SettingsContract.Event, SettingsContract.State, SettingsContract.Effect>(){
    override fun handleEvents(event: SettingsContract.Event) {

    }

    override fun setInitialState()  = SettingsContract.State.default()
}