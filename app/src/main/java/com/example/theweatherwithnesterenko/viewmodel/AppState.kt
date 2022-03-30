package com.example.theweatherwithnesterenko.viewmodel


import com.example.theweatherwithnesterenko.repository.TheWeather

//todo разобраться
sealed class AppState { //FIXME не понял как работает этот класс

    object LoadingProcess : AppState()

    data class Success(val weatherList: List<TheWeather>) : AppState()
    data class FatalError(val error: Throwable) : AppState()


    data class RenderingDataSuccess(val theData: List<TheWeather>) : AppState()
}