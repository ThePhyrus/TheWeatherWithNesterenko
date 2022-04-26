package com.example.theweatherwithnesterenko.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.theweatherwithnesterenko.repository.*


class DetailsViewModel(
    private val liveData: MutableLiveData<DetailsState> = MutableLiveData(),
    private val repository: DetailsRepository = DetailsRepositoryRetrofit2Impl()
) : ViewModel() {

    fun getLiveData() = liveData

    fun getWeather(city: City) {
        liveData.postValue(DetailsState.Loading)
        repository.getWeatherDetails(city, object : Callback {
            override fun onResponse(weather: Weather) {
                liveData.postValue(DetailsState.Success(weather))
            }

            override fun onFail() {
               // TODO liveData.postValue(DetailsState.Error(???))
            }
        })
    }

    interface Callback {
        fun onResponse(weather: Weather)
        fun onFail()
    }
}