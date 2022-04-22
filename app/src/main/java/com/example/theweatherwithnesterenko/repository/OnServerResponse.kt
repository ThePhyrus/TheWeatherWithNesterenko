package com.example.theweatherwithnesterenko.repository

import com.example.theweatherwithnesterenko.repository.dto.WeatherDTO

fun interface OnServerResponse {
    fun onResponse(weatherDTO: WeatherDTO)
}