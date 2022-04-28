package com.example.theweatherwithnesterenko.repository

import com.example.theweatherwithnesterenko.viewmodel.DetailsViewModel


interface DetailsRepositoryAdd {
    fun addWeather(weather: Weather)
}