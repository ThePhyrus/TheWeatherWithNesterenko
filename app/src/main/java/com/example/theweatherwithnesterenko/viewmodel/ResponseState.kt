package com.example.theweatherwithnesterenko.viewmodel

import com.example.theweatherwithnesterenko.repository.Weather


sealed class ResponseState { //FIXME с этим делом разобраться не смог
    object FatalError : ResponseState()
    data class ErrorOnClientSide(val errorMessage:String) : ResponseState()
    data class ErrorOnServerSide(val errorMessage:String) : ResponseState()
}
