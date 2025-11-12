package ru.weather.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import ru.weather.core.comon_ui.ProgressBar
import ru.weather.core.utils.CoreConstants.dimensions
import ru.weather.domain.models.CurrentWeatherModel
import ru.weather.domain.models.ForecastModel
import ru.weather.domain.models.LocationModel
import ru.weather.weather.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun LoadingScreenContent() {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.title_app),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                    )
                },
                actions = {
                    IconButton(
                        onClick = { },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = null,
                            tint = Color.Blue
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(dimensions.d16),
            contentPadding = PaddingValues(dimensions.d16)
        ) {
            item {
                CurrentWeatherSection(
                    current = CurrentWeatherModel.default(),
                    location = LocationModel.default()
                )
            }
            item {
                HourlyForecastSection(ForecastModel.default())
            }
            item {
                ThreeDayForecastSection(ForecastModel(listOf()))
            }
        }
    }
}

@Composable
internal fun DailyForecastItemLoading() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensions.d4)
    ) {
        Text(
            text = stringResource(R.string.zeo_day),
            style = MaterialTheme.typography.bodyMedium
        )
        Box(
            modifier = Modifier.size(dimensions.d40),
            contentAlignment = Alignment.Center
        ) {
            ProgressBar(R.raw.rainy)
        }

        Text(
            text = stringResource(R.string.zero_degree),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.zero_degree),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = stringResource(R.string.zero_procent),
            style = MaterialTheme.typography.bodySmall,
            color = Color.Blue
        )
    }
}


@Composable
internal fun HourlyForecastItemLoading() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensions.d4)
    ) {

        Text(
            text = stringResource(R.string.zero_hours),
            style = MaterialTheme.typography.bodyMedium
        )
        Box(
            modifier = Modifier.size(dimensions.d40),
            contentAlignment = Alignment.Center
        ) {
            ProgressBar(R.raw.sunny)

        }

        Text(
            text = stringResource(R.string.zero_degree),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = stringResource(R.string.zero_speed),
            style = MaterialTheme.typography.bodySmall,
            color = Color.Blue
        )
    }
}