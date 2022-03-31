package com.example.theweatherwithnesterenko.repository

interface Facade {
    fun getWeatherDataFromServer():Weather
    fun getWeatherDataFromLocalStorageWorld():List<Weather>
    fun getWeatherDataFromLocalStorageRussia():List<Weather>
}