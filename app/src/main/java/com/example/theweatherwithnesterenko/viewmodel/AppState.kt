package com.example.theweatherwithnesterenko.viewmodel


import com.example.theweatherwithnesterenko.repository.TheWeather

//todo разобраться
sealed class AppState { //FIXME не понял как работает этот класс
    object Loading : AppState()
    data class Success(val theWeatherList: List<TheWeather>) : AppState()
    data class Error(val error: Throwable) : AppState()
}