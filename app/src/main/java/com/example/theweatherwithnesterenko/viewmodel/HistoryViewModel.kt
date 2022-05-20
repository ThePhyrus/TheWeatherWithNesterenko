package com.example.theweatherwithnesterenko.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.theweatherwithnesterenko.repository.impl.DetailsRepositoryRoomImpl
import com.example.theweatherwithnesterenko.repository.weather.Weather
import com.example.theweatherwithnesterenko.utils.TAG
import com.example.theweatherwithnesterenko.viewmodel.states.AppState


class HistoryViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: DetailsRepositoryRoomImpl = DetailsRepositoryRoomImpl()
) :
    ViewModel() {

    fun getData(): LiveData<AppState> {
        return liveData
    }

    fun getAll() {
        repository.getAllWeatherDetails(object : CallbackForAll {
            override fun onResponse(weatherList: List<Weather>) {
                liveData.postValue(AppState.Success(weatherList))
                Log.d(TAG, "onResponse() called with: listWeather = $weatherList")
            }

            override fun onFail() {
                Log.d(TAG, "onFail() called")
            }

        })
    }

    interface CallbackForAll {
        fun onResponse(weatherList: List<Weather>)
        fun onFail()
    }


}