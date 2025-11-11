package ru.weather.core.ext

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import ru.weather.core.navigation.LocalNavigationManager
import ru.weather.core.navigation.NavigationManager
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String.formatDateToShort(): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("EEE", Locale.getDefault())
        val date = inputFormat.parse(this)
        outputFormat.format(date ?: Date())
    } catch (e: Exception) {
        this
    }
}
