package ru.weather.cities

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.weather.core.base.BaseViewModel
import ru.weather.core.navigation.NavigationManager
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(
    private val navigationManager: NavigationManager
): BaseViewModel<CitiesContract.Event, CitiesContract.State, CitiesContract.Effect>(){
    override fun handleEvents(event: CitiesContract.Event) {

    }
    override fun setInitialState()  = CitiesContract.State.default()
}