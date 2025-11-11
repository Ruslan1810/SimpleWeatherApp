package ru.weather.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.hilt.navigation.compose.hiltViewModel
import ru.weather.core.base.ScreenRoute
import ru.weather.models.StateEventEffectModel


internal val localStateEventEffectModel = compositionLocalOf<StateEventEffectModel> {
    error("No StateEventEffectModel class found!")
}
@Composable
internal fun WeatherNavGraph() {
    val viewModel = hiltViewModel<WeatherScreenViewModel>()

    ScreenRoute(viewModel = viewModel) { state, onEvent ->
        val model = StateEventEffectModel(state = state, event = onEvent)

        CompositionLocalProvider(localStateEventEffectModel provides model) {
            WeatherScreen()
        }
    }
}