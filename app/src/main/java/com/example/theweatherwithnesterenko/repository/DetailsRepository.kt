package com.example.theweatherwithnesterenko.repository

import com.example.theweatherwithnesterenko.viewmodel.DetailsViewModel


interface DetailsRepository {
    fun getWeatherDetails(city: City, callback: DetailsViewModel.Callback)
}