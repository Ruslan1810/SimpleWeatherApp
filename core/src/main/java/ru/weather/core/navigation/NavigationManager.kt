package ru.weather.core.navigation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import ru.weather.core.navigation.model.NavigationAction
import ru.weather.core.navigation.model.NavigationCommand
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationManager @Inject constructor() {
    private var _commands =
        MutableSharedFlow<NavigationCommand>(
            1,
            1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST,
        )
    val commands = _commands.asSharedFlow()

    fun navigate(direction: String) {
        _commands.tryEmit(NavigationCommand(NavigationAction.FORWARD, direction))
    }

    fun finish() {
        _commands.tryEmit(NavigationCommand(NavigationAction.FINISH))
    }

    fun back() {
        _commands.tryEmit(NavigationCommand(NavigationAction.BACK))
    }

    fun replace(direction: String) {
        _commands.tryEmit(NavigationCommand(NavigationAction.REPLACE, direction))
    }

    fun replaceCurrent(direction: String) {
        _commands.tryEmit(NavigationCommand(NavigationAction.REPLACE_CURRENT, direction))
    }

    fun logout() {
        _commands.tryEmit(NavigationCommand(NavigationAction.LOGOUT))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun resetCache() {
        _commands.resetReplayCache()
    }
}