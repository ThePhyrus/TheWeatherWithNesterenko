package com.example.theweatherwithnesterenko.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.theweatherwithnesterenko.repository.*
import com.example.theweatherwithnesterenko.utils.TAG


class DetailsViewModel(
    private val liveData: MutableLiveData<DetailsState> = MutableLiveData(),
    private val repositoryAdd: DetailsRepositoryAdd = DetailsRepositoryRoomImpl(),
) : ViewModel() {
    private var repositoryOne: DetailsRepositoryOne = DetailsRepositoryOneRetrofit2Impl()

    fun getLiveData() = liveData

    fun getWeather(city: City) {
        liveData.postValue(DetailsState.Loading)
        if (isInternet()) {
            repositoryOne = DetailsRepositoryOneRetrofit2Impl()
        } else {
            DetailsRepositoryRoomImpl()
        }
            repositoryOne.getWeatherDetails(city, object : Callback {
                override fun onResponse(weather: Weather) {
                    liveData.postValue(DetailsState.Success(weather))
                    if (isInternet()){
                            repositoryAdd.addWeather(weather)
                        Log.d(TAG, "onResponse() called with internet: weather = $weather")
                    } else{
                        Log.d(TAG, "onResponse() called without internet: weather = $weather")
                    }
                }

                override fun onFail() {
                    Log.d(TAG, "onFail() called  inside getWeather()") //todo что-ещё
                }
            })

    }

    private fun isInternet(): Boolean { //todo как-то переписать эту функцию
        // заглушка
        return true
    }

    interface Callback {
        fun onResponse(weather: Weather)
        fun onFail() //todo сделать что-нибудь полезное из этой функции? Или она идеальна? :)
    }

}