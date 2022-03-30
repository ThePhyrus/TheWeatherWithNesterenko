package com.example.theweatherwithnesterenko.view.weatherlist

import com.example.theweatherwithnesterenko.repository.TheWeather

interface OnItemListClickListener {
    fun onItemClick(weather: TheWeather)
}