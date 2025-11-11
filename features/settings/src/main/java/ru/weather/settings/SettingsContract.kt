package ru.weather.settings

import ru.weather.core.base.ViewEvent
import ru.weather.core.base.ViewSideEffect
import ru.weather.core.base.ViewState

class SettingsContract {
    data class State(
        val isDarkTheme: Boolean,
        val notificationsEnabled: Boolean,
        val temperatureUnit: String,
        val language: String,
        val isLoading: Boolean
    ) : ViewState {

        companion object {
            fun default() = State(
                isDarkTheme = false,
                notificationsEnabled = true,
                temperatureUnit = "Celsius",
                language = "English",
                isLoading = false
            )
        }
    }

    sealed class Event : ViewEvent {
        data object OnProfileClick : Event()
        data object OnNotificationsClick : Event()
        data object OnThemeClick : Event()
        data object OnAboutClick : Event()
        data object OnBackClick : Event()
        data class OnThemeChanged(val isDark: Boolean) : Event()
        data class OnNotificationsChanged(val enabled: Boolean) : Event()
    }

    sealed class Effect : ViewSideEffect
}