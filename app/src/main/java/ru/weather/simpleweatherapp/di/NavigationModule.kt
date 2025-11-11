package ru.weather.simpleweatherapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import ru.weather.core.navigation.FeatureApi
import ru.weather.navigation.WeatherFeatureApi
import ru.weather.cities.navigation.CitiesFeatureApi
import ru.weather.settings.navigation.SettingsFeatureApi

@Module
@InstallIn(SingletonComponent::class)
abstract class NavigationModule {

    @Binds
    @IntoSet
    abstract fun bindCitiesFeature(feature: CitiesFeatureApi): FeatureApi

    @Binds
    @IntoSet
    abstract fun bindWeatherFeature(feature: WeatherFeatureApi): FeatureApi

    @Binds
    @IntoSet
    abstract fun bindSettingsFeature(feature: SettingsFeatureApi): FeatureApi
}