package ru.weather.core.comon_ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import ru.weather.core.R
import ru.weather.core.comon_ui.model.ErrorType
import ru.weather.core.utils.CoreConstants.dimensions

@Composable
fun ErrorDialog(
    errorType: ErrorType,
    onRetry: () -> Unit,
    onDismiss: () -> Unit = {}
) {
    val (title, message) = when (errorType) {
        ErrorType.NETWORK -> Pair(
            stringResource(R.string.error_network_title),
            stringResource(R.string.error_network_desc)
        )

        ErrorType.SERVER -> Pair(
            stringResource(R.string.error_server_title),
            stringResource(R.string.error_server_desc)
        )

        ErrorType.UNKNOWN -> Pair(
            stringResource(R.string.error_unknow_title),
            stringResource(R.string.error_unknow_desc)
        )
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    modifier = Modifier.size(dimensions.d24),
                    tint = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.width(dimensions.d8))
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        },
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.alert_btn_reboot))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.alert_btn_exit))
            }
        }
    )
}

