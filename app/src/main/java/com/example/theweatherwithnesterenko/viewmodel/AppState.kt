package com.example.theweatherwithnesterenko.viewmodel


import com.example.theweatherwithnesterenko.repository.Weather

//todo разобраться
sealed class AppState { //FIXME не понял как работает этот класс
    object Loading : AppState()
    data class Success(val weatherList: List<Weather>) : AppState()
    data class Error(val error: Throwable) : AppState()
}