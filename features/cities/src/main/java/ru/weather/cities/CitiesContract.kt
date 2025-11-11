package ru.weather.cities

import ru.weather.core.base.ViewEvent
import ru.weather.core.base.ViewSideEffect
import ru.weather.core.base.ViewState

class CitiesContract {
    data class State(
        val isLoading: Boolean = false,
        val error: String? = null
    ) : ViewState {

        companion object {
            fun default() = State(
                isLoading = false
            )
        }
    }

    sealed class Event : ViewEvent {
        data object OnSearchClick : Event()
        data object OnAddCityClick : Event()
        data class OnCityClick(val cityId: String) : Event()
        data object OnRefresh : Event()
        data class OnDeleteCity(val cityId: String) : Event()
    }

    sealed class Effect : ViewSideEffect
}