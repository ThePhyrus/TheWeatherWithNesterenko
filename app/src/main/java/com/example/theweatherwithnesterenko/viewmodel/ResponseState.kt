package com.example.theweatherwithnesterenko.viewmodel

import com.example.theweatherwithnesterenko.repository.Weather


sealed class ResponseState {
    object Error1 : ResponseState()
    data class Error2(val weatherList: List<Weather>) : ResponseState()
    data class Error3(val error: Throwable) : ResponseState()
}