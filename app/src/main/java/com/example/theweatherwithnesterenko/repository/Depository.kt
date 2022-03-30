package com.example.theweatherwithnesterenko.repository

interface Depository {
    fun getWeatherFromServer():TheWeather
    fun getWorldWeatherFromLocalStorage():List<TheWeather>
    fun getRussianWeatherFromLocalStorage():List<TheWeather>
}