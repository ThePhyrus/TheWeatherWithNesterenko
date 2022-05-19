package com.example.theweatherwithnesterenko.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.theweatherwithnesterenko.repository.repo.DetailsRepositoryAdd
import com.example.theweatherwithnesterenko.repository.repo.DetailsRepositoryOne
import com.example.theweatherwithnesterenko.repository.impl.DetailsRepositoryRetrofit2Impl
import com.example.theweatherwithnesterenko.repository.impl.DetailsRepositoryRoomImpl
import com.example.theweatherwithnesterenko.repository.weather.City
import com.example.theweatherwithnesterenko.repository.weather.Weather
import com.example.theweatherwithnesterenko.utils.TAG
import com.example.theweatherwithnesterenko.viewmodel.states.DetailsState


class DetailsViewModel(
    private val liveData: MutableLiveData<DetailsState> = MutableLiveData(),
    private val repositoryAdd: DetailsRepositoryAdd = DetailsRepositoryRoomImpl(),
) : ViewModel() {
    private var repositoryOne: DetailsRepositoryOne = DetailsRepositoryRetrofit2Impl()

    fun getLiveData() = liveData

    fun getWeather(city: City) {
        liveData.postValue(DetailsState.Loading)
        if (isInternet()) {
            repositoryOne = DetailsRepositoryRetrofit2Impl()
        } else {
            DetailsRepositoryRoomImpl()
        }

        repositoryOne.getWeatherDetails(city, object : Callback {
            override fun onResponse(weather: Weather) {
                liveData.value = (DetailsState.Success(weather)) //FIXME postValue??
                if (isInternet()) {
                    Thread {
                        repositoryAdd.addWeather(weather)
                    }.start()
                } else {
                    Log.d(TAG, "onResponse() called") //todo что-ещё
                }
            }

            override fun onFail() {
                Log.d(TAG, "onFail() called") //todo что-ещё
            }
        })

    }

    private fun isInternet(): Boolean { //todo как-то переписать эту функцию
        // заглушка
        return true //todo если будет false, то в историю запросов ничего добавляться не будет. Почему? Зачем?
    }

    interface Callback {
        fun onResponse(weather: Weather)
        fun onFail() //todo сделать что-нибудь полезное из этой функции? Или она идеальна? :)
    }

}