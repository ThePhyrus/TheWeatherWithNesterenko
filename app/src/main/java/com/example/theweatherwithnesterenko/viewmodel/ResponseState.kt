package com.example.theweatherwithnesterenko.viewmodel

import com.example.theweatherwithnesterenko.repository.Weather


sealed class ResponseState {
    object FatalError : ResponseState()
    data class ErrorOnClientSide(val errorMessage:String) : ResponseState()
    data class ErrorOnServerSide(val errorMessage:String) : ResponseState()
}
