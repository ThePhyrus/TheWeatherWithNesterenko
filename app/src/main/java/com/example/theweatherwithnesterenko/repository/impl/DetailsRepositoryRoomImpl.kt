package com.example.theweatherwithnesterenko.repository.impl

import com.example.theweatherwithnesterenko.MainApp
import com.example.theweatherwithnesterenko.repository.repo.DetailsRepositoryAdd
import com.example.theweatherwithnesterenko.repository.repo.DetailsRepositoryAll
import com.example.theweatherwithnesterenko.repository.repo.DetailsRepositoryOne
import com.example.theweatherwithnesterenko.repository.weather.City
import com.example.theweatherwithnesterenko.repository.weather.Weather
import com.example.theweatherwithnesterenko.utils.convertHistoryEntityToWeather
import com.example.theweatherwithnesterenko.utils.convertWeatherToEntity
import com.example.theweatherwithnesterenko.viewmodel.DetailsViewModel
import com.example.theweatherwithnesterenko.viewmodel.HistoryViewModel

class DetailsRepositoryRoomImpl : DetailsRepositoryOne, DetailsRepositoryAll, DetailsRepositoryAdd {
    override fun getAllWeatherDetails(callback: HistoryViewModel.CallbackForAll) {
        Thread {
            callback.onResponse(convertHistoryEntityToWeather(MainApp.getHistoryDao().getAll()))
        }.start()
    }

    override fun getWeatherDetails(city: City, callback: DetailsViewModel.Callback) {
        val list = convertHistoryEntityToWeather(MainApp.getHistoryDao().getHistoryCity(city.name))
        if (list.isEmpty()) {
            callback.onFail()
        } else {
            callback.onResponse(list.last())
        }
    }

    override fun addWeather(weather: Weather) {
        MainApp.getHistoryDao().insert(convertWeatherToEntity(weather))
    }

}