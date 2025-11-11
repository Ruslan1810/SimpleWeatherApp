package ru.weather.simpleweatherapp

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import retrofit2.HttpException
import ru.weather.core.comon_ui.model.ErrorType
import ru.weather.domain.models.LocationModel
import ru.weather.domain.models.WeatherDataModel
import ru.weather.domain.usecase.GetLocationUseCase
import ru.weather.domain.usecase.GetWeatherUseCase
import ru.weather.presentation.LoadingResult
import ru.weather.presentation.WeatherScreenViewModel
import java.io.IOException

@ExperimentalCoroutinesApi
class WeatherScreenViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: WeatherScreenViewModel
    private val mockUseCase: GetWeatherUseCase = mockk()
    private val mockUseCase2: GetLocationUseCase = mockk()

    /** Тест смены состояний */
    @Test
    fun `should emit loading then success states in correct order`() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        try {
            coEvery {
                mockUseCase.getWeatherData(
                    any(),
                    any(),
                    any(),
                    any()
                )
            } returns Result.success(WeatherDataModel.default())

            viewModel = WeatherScreenViewModel(mockUseCase, mockUseCase2)

            viewModel.viewState.test {
                assertEquals(LoadingResult.LOADING, awaitItem().loadingResult)
                assertEquals(LoadingResult.SUCCESS, awaitItem().loadingResult)
                expectNoEvents()
            }
        } finally {
            Dispatchers.resetMain()
        }
    }

    /** Тест успешной загрузки данных */
    @Test
    fun `should set correct data on successful load`() = runTest {
        val mockData = WeatherDataModel.default().copy(
            location = LocationModel.default().copy(name = "Москва")
        )

        coEvery {
            mockUseCase.getWeatherData(
                any(),
                any(),
                any(),
                any()
            )
        } returns Result.success(mockData)

        viewModel = WeatherScreenViewModel(mockUseCase, mockUseCase2)

        assertEquals("Москва", viewModel.currentState.data.location.name)
        assertEquals(mockData, viewModel.viewState.value.data)
    }

    /** Тест сетевой ошибки IOException */
    @Test
    fun `should emit loading then error states when network error occurs`() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        try {
            val networkException = IOException("Проверьте подключение к интернету")
            coEvery {
                mockUseCase.getWeatherData(
                    any(),
                    any(),
                    any(),
                    any()
                )
            } returns Result.failure(networkException)

            viewModel = WeatherScreenViewModel(mockUseCase, mockUseCase2)

            viewModel.viewState.test {
                val loadingState = awaitItem()
                assertEquals(LoadingResult.LOADING, loadingState.loadingResult)

                val errorState = awaitItem()
                assertEquals(LoadingResult.ERROR, errorState.loadingResult)
                assertEquals(ErrorType.NETWORK, errorState.errorType)

                expectNoEvents()
            }

            coVerify(exactly = 1) {
                mockUseCase.getWeatherData(
                    any(),
                    any(),
                    any(),
                    any()
                )
            }
        } finally {
            Dispatchers.resetMain()
        }
    }

    /** Тест серверной ошибки HttpException */
    @Test
    fun `should emit loading then error states when server error occurs`() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        try {
            val serverException = mockk<HttpException>()
            coEvery {
                mockUseCase.getWeatherData(
                    any(),
                    any(),
                    any(),
                    any()
                )
            } returns Result.failure(serverException)

            viewModel = WeatherScreenViewModel(mockUseCase, mockUseCase2)

            viewModel.viewState.test {
                awaitItem()
                val errorState = awaitItem()

                assertEquals(LoadingResult.ERROR, errorState.loadingResult)
                assertEquals(ErrorType.SERVER, errorState.errorType)
            }

            coVerify(exactly = 1) {
                mockUseCase.getWeatherData(
                    any(),
                    any(),
                    any(),
                    any()
                )
            }
        } finally {
            Dispatchers.resetMain()
        }
    }

    /** Тест неизвестной ошибки RuntimeException */
    @Test
    fun `should emit loading then error states when unknown error occurs`() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        try {
            val unknownException = RuntimeException("Неизвестная ошибка")
            coEvery {
                mockUseCase.getWeatherData(
                    any(),
                    any(),
                    any(),
                    any()
                )
            } returns Result.failure(unknownException)

            viewModel = WeatherScreenViewModel(mockUseCase, mockUseCase2)

            viewModel.viewState.test {
                awaitItem()
                val errorState = awaitItem()

                assertEquals(LoadingResult.ERROR, errorState.loadingResult)
                assertEquals(ErrorType.UNKNOWN, errorState.errorType)
            }

            coVerify(exactly = 1) {
                mockUseCase.getWeatherData(
                    any(),
                    any(),
                    any(),
                    any()
                )
            }
        } finally {
            Dispatchers.resetMain()
        }
    }
}

class MainDispatcherRule : TestWatcher() {
    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}