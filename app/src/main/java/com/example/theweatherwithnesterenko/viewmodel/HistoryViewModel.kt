package com.example.theweatherwithnesterenko.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.theweatherwithnesterenko.repository.DetailsRepositoryRoomImpl
import com.example.theweatherwithnesterenko.repository.RepositoryImpl
import com.example.theweatherwithnesterenko.repository.Weather


class HistoryViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: DetailsRepositoryRoomImpl = DetailsRepositoryRoomImpl()
) :
    ViewModel() {

    fun getData(): LiveData<AppState> {
        return liveData
    }

    fun getAll(){
    repository.getAllWeatherDetails(object :CallbackForAll{
        override fun onResponse(listWeather: List<Weather>) {
            liveData.postValue(AppState.Success(listWeather))
        }

        override fun onFail() {
            //todo HW
        }

    })
    }
    interface CallbackForAll {
        fun onResponse(listWeather: List<Weather>)
        fun onFail()
    }


}