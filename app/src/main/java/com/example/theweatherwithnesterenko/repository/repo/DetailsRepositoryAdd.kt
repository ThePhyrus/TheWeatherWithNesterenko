package com.example.theweatherwithnesterenko.repository.repo

import com.example.theweatherwithnesterenko.repository.weather.Weather


interface DetailsRepositoryAdd {
    fun addWeather(weather: Weather)
}