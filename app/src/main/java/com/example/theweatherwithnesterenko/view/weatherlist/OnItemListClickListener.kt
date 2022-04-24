package com.example.theweatherwithnesterenko.view.weatherlist

import com.example.theweatherwithnesterenko.repository.Weather

interface OnItemListClickListener {
    fun onItemClick(weather: Weather)
}