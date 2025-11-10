package ru.weather

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import ru.weather.domain.models.WeatherDataModel
import ru.weather.domain.repository.RemoteRepository
import ru.weather.domain.usecase.GetWeatherUseCase
import java.io.IOException
import java.net.SocketTimeoutException

class GetWeatherUseCaseTest {

    /** Проверка: что UseCase корректно передает успешный результат от Repository в ViewModel*/
    @Test
    fun `should return success when repository returns success`() = runTest {
        val mockRepository = mockk<RemoteRepository>()
        val useCase = GetWeatherUseCase(mockRepository)

        val expectedResult = Result.success(WeatherDataModel.default())

        coEvery {
            mockRepository.getWeatherData(
                any(),
                any(),
                any(),
                any()
            )
        } returns expectedResult


        val actualResult = useCase.getWeatherData(55.7569, 37.6151, 3, "ru")
        assertEquals(expectedResult, actualResult)
    }

    /** Проверка: что UseCase корректно передает ОШИБКИ от Repository к вызывающей стороне*/
    @Test
    fun `should return failure when repository returns network error`() = runTest {
        val mockRepository = mockk<RemoteRepository>()
        val useCase = GetWeatherUseCase(mockRepository)

        val expectedException = IOException("No network connection")
        val expectedResult = Result.failure<WeatherDataModel>(expectedException)

        coEvery {
            mockRepository.getWeatherData(any(), any(), any(), any())
        } returns expectedResult

        val actualResult = useCase.getWeatherData(55.7569, 37.6151, 3, "ru")

        assertEquals(expectedResult, actualResult)
        assertTrue(actualResult.isFailure)
        assertTrue(actualResult.exceptionOrNull() is IOException)
    }

    @Test
    fun `should return failure when repository returns unknown error`() = runTest {
        val mockRepository = mockk<RemoteRepository>()
        val useCase = GetWeatherUseCase(mockRepository)

        val expectedException = RuntimeException("Unexpected error")
        val expectedResult = Result.failure<WeatherDataModel>(expectedException)

        coEvery {
            mockRepository.getWeatherData(any(), any(), any(), any())
        } returns expectedResult

        val actualResult = useCase.getWeatherData(55.7569, 37.6151, 3, "ru")

        assertEquals(expectedResult, actualResult)
        assertTrue(actualResult.isFailure)
        assertTrue(actualResult.exceptionOrNull() is RuntimeException)
    }

    @Test
    fun `should return failure when repository returns timeout error`() = runTest {
        val mockRepository = mockk<RemoteRepository>()
        val useCase = GetWeatherUseCase(mockRepository)

        val expectedException = SocketTimeoutException("Connection timeout")
        val expectedResult = Result.failure<WeatherDataModel>(expectedException)

        coEvery {
            mockRepository.getWeatherData(any(), any(), any(), any())
        } returns expectedResult

        val actualResult = useCase.getWeatherData(55.7569, 37.6151, 3, "ru")

        assertEquals(expectedResult, actualResult)
        assertTrue(actualResult.isFailure)
        assertTrue(actualResult.exceptionOrNull() is SocketTimeoutException)
    }
}