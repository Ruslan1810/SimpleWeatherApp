package ru.weather.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.weather.core.utils.CoreConstants.dimensions


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Настройки",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
//            Text(
//                text = stringResource(R.string.settings),
//                style = MaterialTheme.typography.headlineMedium
//            )

//            Spacer(modifier = Modifier.height(dimensions.d24))
//
//            SettingsItem(
//                title = "Профиль",
//                subtitle = "",
//                onClick = {  }
//            )
//
//            SettingsItem(
//                title = "Темы",
//                subtitle = "Светлая",
//                onClick = {  }
//            )
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = dimensions.d8),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    text = "Вкл/Выкл уведомлен",
//                    style = MaterialTheme.typography.bodyLarge
//                )
//                Switch(
//                    checked = state.notificationsEnabled,
//                    onCheckedChange = { enabled ->
//                        event(SettingsContract.Event.NotificationsToggled(enabled))
//                    }
//                )
//            }
//
//            SettingsItem(
//                title = stringResource(R.string.about_app),
//                subtitle = stringResource(R.string.app_information),
//                onClick = { event(SettingsContract.Event.AboutClicked) }
//            )
        }
    }
}

@Composable
fun SettingsItem(
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensions.d4),
        elevation = CardDefaults.cardElevation(dimensions.d2)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensions.d16),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}