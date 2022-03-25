package com.example.theweatherwithnesterenko.viewmodel


import com.example.theweatherwithnesterenko.repository.Weather

sealed class AppState {
    object Loading : AppState()
    data class Success(val weatherData: Weather) : AppState()
    data class Error(val error: Throwable) : AppState()
}