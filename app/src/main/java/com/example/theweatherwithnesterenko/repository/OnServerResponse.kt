package com.example.theweatherwithnesterenko.repository

fun interface OnServerResponse {
    fun onResponse(weatherDTO: WeatherDTO)
}