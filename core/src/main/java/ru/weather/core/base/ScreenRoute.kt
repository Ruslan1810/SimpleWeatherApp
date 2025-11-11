package ru.weather.core.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.Flow


@Composable
fun <Event : ViewEvent, UiState : ViewState, Effect : ViewSideEffect> ScreenRoute(
    viewModel: BaseViewModel<Event, UiState, Effect>,
    content: @Composable (state: UiState, onEventSent: (Event) -> Unit) -> Unit,
) {
    val state: UiState by viewModel.viewState.collectAsStateWithLifecycle()

    content(
        state,
        viewModel::setEvent,
    )
}

@Composable
fun <Event : ViewEvent, UiState : ViewState, Effect : ViewSideEffect> ScreenRoute(
    viewModel: BaseViewModel<Event, UiState, Effect>,
    content: @Composable (
        state: UiState,
        onEventSent: (Event) -> Unit,
        effect: Flow<Effect>,
    ) -> Unit,
) {
    val state: UiState by viewModel.viewState.collectAsStateWithLifecycle()

    content(
        state,
        viewModel::setEvent,
        viewModel.effect,
    )
}


