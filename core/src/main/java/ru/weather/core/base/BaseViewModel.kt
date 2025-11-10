package ru.weather.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface ViewState

interface ViewEvent

interface ViewSideEffect

abstract class BaseViewModel<
        Event : ViewEvent,
        UiState : ViewState,
        Effect : ViewSideEffect,
        > :
    ViewModel() {

    private val initialState: UiState by lazy { setInitialState() }
    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    private val event: SharedFlow<Event> = _event
    private val _effect: Channel<Effect> = Channel(Channel.BUFFERED)
    private val _viewState = MutableStateFlow(initialState)

    val currentState: UiState by _viewState::value

    val viewState = _viewState.stateIn(viewModelScope, SharingStarted.Eagerly, initialState)
    val effect = _effect.receiveAsFlow()

    init {
        subscribeToEvents()
    }

    fun setEvent(event: Event) {
        viewModelScope.launch { _event.emit(event) }
    }

    protected fun setState(reducer: UiState.() -> UiState) {
        _viewState.update { it.reducer() }
    }

    protected fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        viewModelScope.launch { _effect.send(effectValue) }
    }

    private fun subscribeToEvents() {
        viewModelScope.launch { _event.collect(::handleEvents) }
    }

    abstract fun handleEvents(event: Event)

    abstract fun setInitialState(): UiState
}
