package com.example.theweatherwithnesterenko.repository

import com.example.theweatherwithnesterenko.viewmodel.states.ResponseState

fun interface OnServerResponseListener {
    fun onError(error: ResponseState)
}