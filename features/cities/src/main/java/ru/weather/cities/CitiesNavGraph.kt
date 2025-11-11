package ru.weather.cities

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.hilt.navigation.compose.hiltViewModel
import ru.weather.cities.models.StateEventEffectModel
import ru.weather.core.base.ScreenRoute


internal val localStateEventEffectModel = compositionLocalOf<StateEventEffectModel> {
    error("No StateEventEffectModel class found!")
}
@Composable
internal fun CitiesNavGraph() {
    val viewModel = hiltViewModel<CitiesViewModel>()

    ScreenRoute(viewModel = viewModel) { state, onEvent ->
        val model = StateEventEffectModel(state = state, event = onEvent)

        CompositionLocalProvider(localStateEventEffectModel provides model) {
            CitiesScreen()
        }
    }
}