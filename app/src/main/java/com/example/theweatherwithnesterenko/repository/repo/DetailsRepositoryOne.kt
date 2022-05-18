package com.example.theweatherwithnesterenko.repository.repo

import com.example.theweatherwithnesterenko.repository.weather.City
import com.example.theweatherwithnesterenko.viewmodel.DetailsViewModel


interface DetailsRepositoryOne {
    fun getWeatherDetails(city: City, callback: DetailsViewModel.Callback)
}