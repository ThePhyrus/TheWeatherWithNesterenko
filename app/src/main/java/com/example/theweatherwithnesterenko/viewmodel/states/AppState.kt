package com.example.theweatherwithnesterenko.viewmodel.states

import com.example.theweatherwithnesterenko.repository.weather.Weather


sealed class AppState {
    object Loading : AppState()
    data class Success(val weatherList: List<Weather>) : AppState()
    data class Error(val error: Throwable) : AppState()
}