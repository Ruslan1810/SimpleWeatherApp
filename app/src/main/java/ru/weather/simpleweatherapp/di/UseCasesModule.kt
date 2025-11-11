package ru.weather.simpleweatherapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.weather.domain.repository.LocationRepository
import ru.weather.domain.repository.RemoteRepository
import ru.weather.domain.usecase.GetLocationUseCase
import ru.weather.domain.usecase.GetWeatherUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCasesModule {

    @Provides
    @Singleton
    fun provideGetWeatherUseCase (repository: RemoteRepository): GetWeatherUseCase {
        return GetWeatherUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetLocationUseCase(repository: LocationRepository): GetLocationUseCase {
        return GetLocationUseCase(repository)
    }
}



