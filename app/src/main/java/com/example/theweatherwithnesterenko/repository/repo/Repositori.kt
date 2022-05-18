package com.example.theweatherwithnesterenko.repository.repo

import com.example.theweatherwithnesterenko.repository.weather.Weather

interface Repository {
    fun getWeatherFromServer(): Weather
    fun getWorldWeatherFromLocalStorage(): List<Weather>
    fun getRussianWeatherFromLocalStorage(): List<Weather>
}