package ru.weather.simpleweatherapp.services.notification

import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

class NotificationPermissionManager(private val activity: ComponentActivity) {

    private val requestPermissionLauncher = activity.registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        onPermissionResult?.invoke(isGranted)
    }

    private var onPermissionResult: ((Boolean) -> Unit)? = null

    /** Проверяет и запрашивает разрешение на уведомления */
    fun checkAndRequestNotificationPermission(onResult: (Boolean) -> Unit) {
        this.onPermissionResult = onResult

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                hasNotificationPermission() -> {
                    onResult(true)
                }
                activity.shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS) -> {
                    showPermissionExplanation()
                }
                else -> {
                    requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        } else {
            onResult(true)
        }
    }

    /** Проверяет, есть ли уже разрешение */
    private fun hasNotificationPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                activity,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    private fun showPermissionExplanation() {
        AlertDialog.Builder(activity)
            .setTitle("Разрешение на уведомления")
            .setMessage("Для показа ежечасных уведомлений о погоде необходимо разрешение")
            .setPositiveButton("Разрешить") { _, _ ->
                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
            .setNegativeButton("Позже") { _, _ ->
                onPermissionResult?.invoke(false)
            }
            .setOnCancelListener {
                onPermissionResult?.invoke(false)
            }
            .show()
    }
}