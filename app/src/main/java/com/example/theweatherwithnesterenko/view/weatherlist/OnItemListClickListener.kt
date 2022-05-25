package com.example.theweatherwithnesterenko.view.weatherlist

import com.example.theweatherwithnesterenko.repository.weather.Weather

interface OnItemListClickListener {
    fun onItemClick(weather: Weather)
}