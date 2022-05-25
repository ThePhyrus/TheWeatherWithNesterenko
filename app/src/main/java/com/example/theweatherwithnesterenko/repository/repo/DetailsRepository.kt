package com.example.theweatherwithnesterenko.repository.repo

import com.example.theweatherwithnesterenko.repository.weather.City
import com.example.theweatherwithnesterenko.viewmodel.DetailsViewModel


interface DetailsRepository {
    fun getWeatherDetails(city: City, callbackForOne: DetailsViewModel.CallbackForOne)
}