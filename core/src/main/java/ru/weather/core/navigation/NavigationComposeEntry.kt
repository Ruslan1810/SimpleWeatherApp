package ru.weather.core.navigation

import androidx.navigation.NamedNavArgument

interface NavigationComposeEntry {
    val baseRoute: String

    val arguments: List<NamedNavArgument>
        get() = emptyList()

    fun getComposableRoute() = if (arguments.isEmpty()) {
        baseRoute
    } else {
        buildString {
            append(baseRoute)
            for (arg in arguments) {
                append('/')
                append('{')
                append(arg.name)
                append('}')
            }
        }
    }

    fun getNavigationRoute(vararg args: Any?) = if (args.isEmpty()) {
        baseRoute
    } else {
        buildString {
            append(baseRoute)
            for (arg in args) {
                append('/')
                append(arg)
            }
        }
    }
}