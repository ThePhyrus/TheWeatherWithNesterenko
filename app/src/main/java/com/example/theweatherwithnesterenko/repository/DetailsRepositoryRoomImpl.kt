package com.example.theweatherwithnesterenko.repository

import com.example.theweatherwithnesterenko.MyApp
import com.example.theweatherwithnesterenko.utils.convertHistoryEntityToWeather
import com.example.theweatherwithnesterenko.utils.convertWeatherToEntity
import com.example.theweatherwithnesterenko.viewmodel.DetailsViewModel
import com.example.theweatherwithnesterenko.viewmodel.HistoryViewModel

class DetailsRepositoryRoomImpl : DetailsRepositoryOne, DetailsRepositoryAll, DetailsRepositoryAdd {
    override fun getAllWeatherDetails(callback: HistoryViewModel.CallbackForAll) {
        callback.onResponse(convertHistoryEntityToWeather(MyApp.getHistoryDao().getAll()))
    }

    override fun getWeatherDetails(city: City, callback: DetailsViewModel.Callback) {
        val list = convertHistoryEntityToWeather(MyApp.getHistoryDao().getHistoryCity(city.name))
        callback.onResponse(list.last()) // hack
    }

    override fun addWeather(weather: Weather) {
        MyApp.getHistoryDao().insert(convertWeatherToEntity(weather))
    }

}