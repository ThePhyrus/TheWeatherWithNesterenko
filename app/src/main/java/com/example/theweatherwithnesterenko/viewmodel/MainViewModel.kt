package com.example.theweatherwithnesterenko.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.theweatherwithnesterenko.repository.impl.RepositoryImpl
import com.example.theweatherwithnesterenko.viewmodel.states.AppState


class MainViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: RepositoryImpl = RepositoryImpl()
) :
    ViewModel() {

    fun getData(): LiveData<AppState> {
        return liveData
    }

    fun getWeatherRussia() = getWeather(true)
    fun getWeatherWorld() = getWeather(false)

    private fun getWeather(isRussian: Boolean) {
        Thread {
            liveData.postValue(AppState.Loading)
            if (true) {
                val answer =
                    if (!isRussian) repository.getWorldWeatherFromLocalStorage()
                    else repository.getRussianWeatherFromLocalStorage()
                liveData.postValue(AppState.Success(answer))
            } else
                liveData.postValue(AppState.Error(IllegalAccessException()))
        }.start()
    }
}