# Simple Weather App 🌤️

Простое Android приложение для просмотра погоды, разработанное с использованием современных технологий Android разработки.

<p align="center">
  <img src="https://img.shields.io/badge/Kotlin-2.0.21-blue?logo=kotlin" alt="Kotlin">
  <img src="https://img.shields.io/badge/Android-14-green?logo=android" alt="Android">
  <img src="https://img.shields.io/badge/AGP-8.8.2-red?logo=android" alt="Android Gradle Plugin">
  <img src="https://img.shields.io/badge/Compose-BOM%202024.04.01-orange?logo=jetpackcompose" alt="Jetpack Compose">
  <img src="https://img.shields.io/badge/Hilt-2.51.1-purple?logo=dagger" alt="Dagger Hilt">
  <img src="https://img.shields.io/badge/Retrofit-2.9.0-lightgrey?logo=square" alt="Retrofit">
  <img src="https://img.shields.io/badge/Coroutines-1.7.3-darkblue?logo=kotlin" alt="Coroutines">
  <img src="https://img.shields.io/badge/License-MIT-yellow" alt="License">
</p>

## 📸 Скриншоты

<div align="center">
  <table>
    <tr>
      <td align="center">
        <img src="https://private-user-images.githubusercontent.com/52200692/512080191-b3f6b302-51e3-4ce6-bcb2-5aec4cfdd448.jpg?jwt=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3NjI3Njg3MzYsIm5iZiI6MTc2Mjc2ODQzNiwicGF0aCI6Ii81MjIwMDY5Mi81MTIwODAxOTEtYjNmNmIzMDItNTFlMy00Y2U2LWJjYjItNWFlYzRjZmRkNDQ4LmpwZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNTExMTAlMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjUxMTEwVDA5NTM1NlomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTVmYjc0Y2NlZTE2MDIwMjI3MGJkN2MwZTdlMTJkZDQwMGE4Yzg2NWE4OWFhM2E2ZDUyMzA3YTkwOTAzMWI5NmImWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0In0.bjh-yJYw5xVjnsRQiCTeyV4vJZQEJk2lMrdPApNIfJg" width="200" alt="Главный экран">
        <br>
        <strong>🏠 Главный экран</strong>
      </td>
      <td align="center">
        <img src="https://private-user-images.githubusercontent.com/52200692/512372232-c938d467-c91d-4913-9f32-dc441605ce47.jpg?jwt=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3NjI4MDY1MTYsIm5iZiI6MTc2MjgwNjIxNiwicGF0aCI6Ii81MjIwMDY5Mi81MTIzNzIyMzItYzkzOGQ0NjctYzkxZC00OTEzLTlmMzItZGM0NDE2MDVjZTQ3LmpwZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNTExMTAlMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjUxMTEwVDIwMjMzNlomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTc3ZTNiMWQ2NmJhMGI0ZmViOTI2MTM0NjBjY2RjOTM1NjAwNDgzY2E5ODc2OTkzM2NjYzE5YWUyNGNiZGQwZGMmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0In0.UUMWlc7KLrsCvB38_SACVH6ri-vRQsjwMBRxbwcZGd8" width="200" alt="Экран загрузки">
        <br>
        <strong>⏳ Загрузка данных</strong>
      </td>
      <td align="center">
        <img src="https://private-user-images.githubusercontent.com/52200692/512369391-ef1930ec-f468-4b52-988e-b141c4c3dd99.jpg?jwt=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3NjI4MDYyNDksIm5iZiI6MTc2MjgwNTk0OSwicGF0aCI6Ii81MjIwMDY5Mi81MTIzNjkzOTEtZWYxOTMwZWMtZjQ2OC00YjUyLTk4OGUtYjE0MWM0YzNkZDk5LmpwZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNTExMTAlMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjUxMTEwVDIwMTkwOVomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTY2ZGZjNGRkNWY2YmYwYTM0MTBmYzAwZDdhY2YyZGMyYmU5MTYyMzExNzgzMzEyMWI5Y2JkZWE0MTU5ZDNjYjQmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0In0.l_vqzwbkJPcZJn-y0VLkQghcfzdY9dMp9wRM1DflAI8" width="200" alt="Экран ошибки">
        <br>
        <strong>⚠️ Ошибка соединения</strong>
      </td>
    </tr>
  </table>
</div>


## ✨ Особенности
- ☀️ Текущая погода, почасовой прогноз и прогноз на 3 дня 
- 🎨 Красивый и современный UI с анимациями
- 🌙 Поддержка темной темы
- 🔄 Каждый час приложение отправляет уведомление о погоде
- 📱 Адаптивный дизайн
- 🔄 Обновление данных по кнопке
- 📍 Возможно определение местоположения
- 🧪 Протестированы useceses и viewmodel
- ⚡ Подключен и настроен CI/CD

## 🛠 Технологии

- **Язык:** Kotlin
- **UI:** Jetpack Compose, Material Design 3
- **Архитектура:** MVVM+UDF, Clean Architecture
- **DI:** Dagger Hilt
- **Асинхронность:** Kotlin Coroutines, Flow
- **Сеть:** Retrofit, OkHttp
- **Анимации:** Lottie
- **Локация:** Google Play Services Location

### Установка
 Клонируйте репозиторий:
```bash
git clone https://github.com/Ruslan1810/SimpleWeatherApp.git
