package com.example.theweatherwithnesterenko.viewmodel.states


import com.example.theweatherwithnesterenko.repository.Weather

//todo разобраться
sealed class AppState { //FIXME не понял как работает этот класс

    object LoadingProcess : AppState()

    data class Success(val myListWeather: List<Weather>) : AppState()
    data class FatalError(val fatalError: Throwable) : AppState()


    data class RenderingSuccess (val renderingData: List<Weather>):AppState()

}