package ru.weather.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.weather.data.GeoLocationManager
import ru.weather.data.datasource.remotesource.RemoteDataSource
import ru.weather.data.datasource.remotesource.RemoteDataSourceImpl
import ru.weather.data.repository.GeoLocationManagerImpl
import ru.weather.data.repository.LocationRepositoryImpl
import ru.weather.data.repository.RemoteRepositoryImpl
import ru.weather.domain.repository.LocationRepository
import ru.weather.domain.repository.RemoteRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface DataSourceModule {
    @Binds
    @Singleton
    fun bindRemoteRepository(repository: RemoteRepositoryImpl): RemoteRepository

    @Binds
     @Singleton
     fun bindRemoteDataSource(impl: RemoteDataSourceImpl): RemoteDataSource

    @Binds
    @Singleton
    fun bindLocationRepository(repository: LocationRepositoryImpl): LocationRepository

    @Binds
    @Singleton
    fun bindLocationManager(repository: GeoLocationManagerImpl): GeoLocationManager
}
