package com.example.theweatherwithnesterenko.repository

import android.util.Log
import com.example.theweatherwithnesterenko.MyApp
import com.example.theweatherwithnesterenko.utils.TAG
import com.example.theweatherwithnesterenko.utils.convertHistoryEntityToWeather
import com.example.theweatherwithnesterenko.utils.convertWeatherToEntity
import com.example.theweatherwithnesterenko.viewmodel.DetailsViewModel
import com.example.theweatherwithnesterenko.viewmodel.HistoryViewModel

class DetailsRepositoryRoomImpl : DetailsRepositoryOne, DetailsRepositoryAll, DetailsRepositoryAdd {
    override fun getAllWeatherDetails(callback: HistoryViewModel.CallbackForAll) {
        Thread{
            callback.onResponse(convertHistoryEntityToWeather(MyApp.getHistoryDao().getAll()))
        }.start()

    }

    override fun getWeatherDetails(city: City, callback: DetailsViewModel.Callback) {
        val list = convertHistoryEntityToWeather(MyApp.getHistoryDao().getHistoryCity(city.name))
        if (list.isEmpty()){ // если лист пустой, то и отображать нечего
            callback.onFail() //todo сделать что-нибудь, кроме логов
            Log.d(TAG, "getWeatherDetails() called with: city = $city, callback = $callback")
        }else {
            callback.onResponse(list.last()) // hack
            Log.d(TAG, "getWeatherDetails() called with: city = $city, callback = $callback")
        }
    }

    override fun addWeather(weather: Weather) {
        MyApp.getHistoryDao().insert(convertWeatherToEntity(weather))
    }

}