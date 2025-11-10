package ru.weather.simpleweatherapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import ru.weather.core.utils.CoreConstants.dimensions
import ru.weather.domain.models.CurrentWeatherModel
import ru.weather.domain.models.ForecastModel
import ru.weather.domain.models.LocationModel
import ru.weather.simpleweatherapp.R

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