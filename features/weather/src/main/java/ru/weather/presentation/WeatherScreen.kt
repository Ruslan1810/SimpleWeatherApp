package ru.weather.presentation

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import ru.weather.core.base.ScreenRoute
import ru.weather.core.comon_ui.ErrorDialog
import ru.weather.core.comon_ui.ProgressBar
import ru.weather.core.ext.formatDateToShort
import ru.weather.core.utils.CoreConstants.digital
import ru.weather.core.utils.CoreConstants.dimensions
import ru.weather.domain.models.CurrentWeatherModel
import ru.weather.domain.models.ForecastDayModel
import ru.weather.domain.models.ForecastModel
import ru.weather.domain.models.HourForecastModel
import ru.weather.domain.models.LocationModel
import ru.weather.domain.models.WeatherDataModel
import ru.weather.models.StateEventEffectModel
import ru.weather.weather.R

@Composable
internal fun WeatherScreen() {
    val viewModel = hiltViewModel<WeatherScreenViewModel>()

    ScreenRoute(viewModel = viewModel) { state, onEvent, effect ->
        val model = StateEventEffectModel(state = state, event = onEvent)
        val context = LocalContext.current

        val locationPermissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            val fineLocationGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
            val coarseLocationGranted =
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false

            if (fineLocationGranted || coarseLocationGranted) {
                onEvent(WeatherScreenContract.Event.RequestLocation)
            } else {
                onEvent(WeatherScreenContract.Event.LocationError)
            }
        }

        LaunchedEffect(Unit) {
            effect.collect { effect ->
                when (effect) {
                    is WeatherScreenContract.Effect.RequestLocationPermission -> {
                        locationPermissionLauncher.launch(
                            arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            )
                        )
                    }

                    is WeatherScreenContract.Effect.ShowLocationError -> {
                        Toast.makeText(context, effect.message, Toast.LENGTH_LONG).show()
                    }

                    is WeatherScreenContract.Effect.ShowToast -> {
                        Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        CompositionLocalProvider(localStateEventEffectModel provides model) {
            MainScreenContent()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScreenContent() {
    val event = localStateEventEffectModel.current.event
    val state = localStateEventEffectModel.current.state

    Scaffold(
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
                        onClick = { event(WeatherScreenContract.Event.RetryLoading) },
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
        when (state.loadingResult) {
            LoadingResult.LOADING -> {
                LoadingScreenContent()
            }

            LoadingResult.SUCCESS -> {
                WeatherContent(
                    weatherData = state.data,
                    paddingValues = paddingValues
                )
            }

            LoadingResult.ERROR -> {
                ErrorDialog(
                    errorType = state.errorType,
                    onRetry = { event(WeatherScreenContract.Event.RetryLoading) },
                    onDismiss = { event(WeatherScreenContract.Event.LoadingData) }
                )
            }
        }
    }
}

@Composable
fun WeatherContent(
    weatherData: WeatherDataModel,
    paddingValues: PaddingValues
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(dimensions.d16),
        contentPadding = PaddingValues(dimensions.d16)
    ) {
        item {
            CurrentWeatherSection(
                current = weatherData.current,
                location = weatherData.location
            )
        }
        item {
            HourlyForecastSection(weatherData.forecast)
        }
        item {
            ThreeDayForecastSection(weatherData.forecast)
        }
    }
}

@Composable
internal fun CurrentWeatherSection(current: CurrentWeatherModel, location: LocationModel) {
    val event = localStateEventEffectModel.current.event

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(dimensions.d4)
    ) {
        Column(
            modifier = Modifier.padding(dimensions.d16),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensions.d12)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    var rotationCount by remember { mutableIntStateOf(digital.i0) }
                    var isRotating by remember { mutableStateOf(false) }
                    val rotationAngle by animateFloatAsState(
                        targetValue = if (isRotating) digital.f360 * digital.i3 else digital.f0,
                        animationSpec = tween(durationMillis = digital.i1000, easing = LinearEasing),
                        finishedListener = {
                            rotationCount = digital.i0
                            isRotating = false
                        }
                    )

                    IconButton(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .size(dimensions.d40)
                            .shadow(
                                elevation = dimensions.d2,
                                shape = CircleShape,
                                clip = true
                            )
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = CircleShape
                            ),
                        onClick = {
                            if (!isRotating) {
                                isRotating = true
                                rotationCount = digital.i3
                            }

                            event(WeatherScreenContract.Event.RequestLocation)
                        },
                    ) {

                        val infiniteTransition = rememberInfiniteTransition()
                        val scale by infiniteTransition.animateFloat(
                            initialValue = digital.f2,
                            targetValue = digital.f3,
                            animationSpec = infiniteRepeatable(
                                animation = tween(digital.i1000),
                                repeatMode = RepeatMode.Reverse
                            )
                        )

                        Image(
                            painter = painterResource(id = R.drawable.ic_location),
                            contentDescription = null,
                            modifier = Modifier
                                .scale(scale)
                                .rotate(rotationAngle)
                        )
                    }
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = location.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.Blue
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimensions.d8)
            ) {
                if (current.condition.icon.isNotEmpty()) {
                    AsyncImage(
                        model = stringResource(R.string.https, current.condition.icon),
                        contentDescription = current.condition.text,
                        modifier = Modifier.size(dimensions.d80)
                    )
                } else {
                    Box(
                        modifier = Modifier.size(dimensions.d80),
                        contentAlignment = Alignment.Center
                    ) {
                        ProgressBar(R.raw.loading)
                    }
                }

                Column(horizontalAlignment = Alignment.Start) {
                    Text(
                        text = stringResource(
                            R.string.temp_celcius,
                            current.tempC.toInt(),
                            stringResource(R.string.сelsius)
                        ),
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = stringResource(
                            R.string.fill_like,
                            current.feelslikeC.toInt(),
                            stringResource(R.string.сelsius)
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Text(
                text = current.condition.text,
                style = MaterialTheme.typography.titleMedium
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                WeatherDetailCard(
                    title = stringResource(R.string.wind),
                    value = stringResource(
                        R.string.windKph,
                        current.windKph.toInt(),
                        stringResource(R.string.km_h)
                    )
                )
                WeatherDetailCard(
                    title = stringResource(R.string.pressure),
                    value = stringResource(
                        R.string.pressureMb,
                        current.pressureMb.toInt(),
                        stringResource(R.string.mb)
                    )
                )
                WeatherDetailCard(
                    title = stringResource(R.string.humidity),
                    value = stringResource(
                        R.string.percentage_humidity,
                        current.humidity,
                        stringResource(R.string.percentage)
                    )
                )
                WeatherDetailCard(
                    title = stringResource(R.string.visibility),
                    value = stringResource(
                        R.string.visKm,
                        current.visKm.toInt(),
                        stringResource(R.string.km)
                    )
                )
            }
        }
    }
}

@Composable
fun HourlyForecastSection(forecast: ForecastModel) {
    val todayForecast = forecast.forecastDays.firstOrNull()
    val hourlyForecast = todayForecast?.hour ?: emptyList()

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(dimensions.d16),
            verticalArrangement = Arrangement.spacedBy(dimensions.d12)
        ) {
            Text(
                text = stringResource(R.string.hourly_forecast_title),
                style = MaterialTheme.typography.headlineSmall
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(dimensions.d16),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (hourlyForecast.isNotEmpty()) {
                    items(hourlyForecast) { hour ->
                        HourlyForecastItem(hour = hour)
                    }
                } else {
                    items(digital.i7) {
                        HourlyForecastItemLoading()
                    }
                }

            }
        }
    }
}

@Composable
fun HourlyForecastItem(hour: HourForecastModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensions.d4)
    ) {

        Text(
            text = hour.time.split(stringResource(R.string.space)).last().take(digital.i5),
            style = MaterialTheme.typography.bodyMedium
        )
        AsyncImage(
            model = stringResource(R.string.https, hour.condition.icon),
            contentDescription = hour.condition.text,
            modifier = Modifier.size(dimensions.d40)
        )

        Text(
            text = stringResource(
                R.string.temp_degree,
                hour.tempC.toInt(),
                stringResource(R.string.degree)
            ),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = stringResource(
                R.string.windKph,
                hour.windKph.toInt(),
                stringResource(R.string.km_h)
            ),
            style = MaterialTheme.typography.bodySmall,
            color = Color.Blue
        )
    }
}

@Composable
fun HourlyForecastItemLoading() {
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

@Composable
fun ThreeDayForecastSection(forecast: ForecastModel) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(dimensions.d16),
            verticalArrangement = Arrangement.spacedBy(dimensions.d12)
        ) {
            Text(
                text = stringResource(R.string.three_day_title),
                style = MaterialTheme.typography.headlineSmall
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(dimensions.d16),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (forecast.forecastDays.isNotEmpty()) {
                    items(forecast.forecastDays) { day ->
                        DailyForecastItem(day = day)
                    }
                } else {
                    items(3) {
                        DailyForecastItemLoading()
                    }
                }

            }
        }
    }
}

@Composable
fun DailyForecastItem(day: ForecastDayModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensions.d4)
    ) {
        Text(
            text = day.date.formatDateToShort(),
            style = MaterialTheme.typography.bodyMedium
        )
        AsyncImage(
            model = stringResource(R.string.https, day.day.condition.icon),
            contentDescription = day.day.condition.text,
            modifier = Modifier.size(dimensions.d40)
        )

        Text(
            text = stringResource(
                R.string.temp_degree,
                day.day.maxTempC.toInt(),
                stringResource(R.string.degree)
            ),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(
                R.string.temp_degree,
                day.day.minTempC.toInt(),
                stringResource(R.string.degree)
            ),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = stringResource(
                R.string.percentage_humidity,
                day.day.dailyChanceOfRain,
                stringResource(R.string.percentage)
            ),
            style = MaterialTheme.typography.bodySmall,
            color = Color.Blue
        )
    }
}

@Composable
fun DailyForecastItemLoading() {
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
fun WeatherDetailCard(title: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensions.d4)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = Color.Blue
        )
    }
}