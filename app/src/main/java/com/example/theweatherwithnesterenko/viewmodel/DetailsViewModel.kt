package com.example.theweatherwithnesterenko.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.theweatherwithnesterenko.repository.repo.DetailsRepositoryAdd
import com.example.theweatherwithnesterenko.repository.repo.DetailsRepository
import com.example.theweatherwithnesterenko.repository.impl.DetailsRepositoryRetrofitImpl
import com.example.theweatherwithnesterenko.repository.impl.DetailsRepositoryRoomImpl
import com.example.theweatherwithnesterenko.repository.weather.City
import com.example.theweatherwithnesterenko.repository.weather.Weather
import com.example.theweatherwithnesterenko.utils.TAG
import com.example.theweatherwithnesterenko.viewmodel.states.DetailsState


class DetailsViewModel(
    private val liveData: MutableLiveData<DetailsState> = MutableLiveData(),
    private val repositoryAdd: DetailsRepositoryAdd = DetailsRepositoryRoomImpl(),
) : ViewModel() {
    private var repositoryOne: DetailsRepository = DetailsRepositoryRetrofitImpl()

    fun getLiveData() = liveData

    fun getWeather(city: City) {
        liveData.postValue(DetailsState.Loading)
        if (isInternet()) {
            repositoryOne = DetailsRepositoryRetrofitImpl()
        } else {
            DetailsRepositoryRoomImpl()
        }

        repositoryOne.getWeatherDetails(city, object : CallbackForOne {
            override fun onResponse(weather: Weather) {
                liveData.postValue(DetailsState.Success(weather)) //FIXME postValue?? или value = ??
                if (isInternet()) {
                    Thread {
                        repositoryAdd.addWeather(weather)
                    }.start()
                } else {
                    Log.d(TAG, "onResponse() called with: weather = $weather")
                }
            }

            override fun onFail() {
                Log.d(TAG, "onFail() called")
            }
        })

    }

    private fun isInternet(): Boolean { //FIXME
        return true //todo если будет false, то в историю запросов ничего добавляться не будет
    }

    interface CallbackForOne {

        fun onResponse(weather: Weather)

        fun onFail(){
            Log.d(TAG, "onFail() called")
        }
    }


}