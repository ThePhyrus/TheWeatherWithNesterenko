package com.example.theweatherwithnesterenko.repository.repo

import com.example.theweatherwithnesterenko.repository.weather.Weather

interface Repository {
    fun getWorldWeatherFromLocalStorage(): List<Weather>
    fun getRussianWeatherFromLocalStorage(): List<Weather>
}