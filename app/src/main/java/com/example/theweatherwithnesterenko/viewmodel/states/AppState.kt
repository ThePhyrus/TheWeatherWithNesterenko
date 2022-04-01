package com.example.theweatherwithnesterenko.viewmodel.states


import com.example.theweatherwithnesterenko.repository.Weather


sealed class AppState {

    object LoadingProcess : AppState()

    data class Success(val myListWeather: List<Weather>) : AppState()
    data class FatalError(val fatalError: Throwable) : AppState()
}