package com.example.theweatherwithnesterenko.view.listeners

import com.example.theweatherwithnesterenko.repository.Weather

interface OnItemListClickListener {
    fun onItemClick(weather: Weather)
}