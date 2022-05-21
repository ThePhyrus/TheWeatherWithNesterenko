package com.example.theweatherwithnesterenko.repository.impl

import android.util.Log
import com.example.theweatherwithnesterenko.MainApp
import com.example.theweatherwithnesterenko.repository.repo.DetailsRepositoryAdd
import com.example.theweatherwithnesterenko.repository.repo.DetailsRepositoryAll
import com.example.theweatherwithnesterenko.repository.repo.DetailsRepository
import com.example.theweatherwithnesterenko.repository.weather.City
import com.example.theweatherwithnesterenko.repository.weather.Weather
import com.example.theweatherwithnesterenko.utils.TAG
import com.example.theweatherwithnesterenko.utils.convertHistoryEntityToWeather
import com.example.theweatherwithnesterenko.utils.convertWeatherToEntity
import com.example.theweatherwithnesterenko.viewmodel.DetailsViewModel
import com.example.theweatherwithnesterenko.viewmodel.HistoryViewModel


class DetailsRepositoryRoomImpl : DetailsRepository, DetailsRepositoryAll, DetailsRepositoryAdd {

    override fun getAllWeatherDetails(callback: HistoryViewModel.CallbackForAll) {
        Thread {
            callback.onResponse(convertHistoryEntityToWeather(MainApp.getHistoryDao().getAll()))
        }.start()
        Log.d(TAG, "getAllWeatherDetails() called with: callback = $callback")
    }

    override fun getWeatherDetails(city: City, callbackForOne: DetailsViewModel.CallbackForOne) {
        val list = convertHistoryEntityToWeather(MainApp.getHistoryDao().getHistoryCity(city.name))
        if (list.isEmpty()) {
            callbackForOne.onFail()
        } else {
            callbackForOne.onResponse(list.last())
        }
        Log.d(
            TAG,
            "getWeatherDetails() called with: city = $city, callbackForOne = $callbackForOne"
        )
    }

    override fun addWeather(weather: Weather) {
        MainApp.getHistoryDao().insert(convertWeatherToEntity(weather))
        Log.d(TAG, "addWeather() called with: weather = $weather")
    }

}