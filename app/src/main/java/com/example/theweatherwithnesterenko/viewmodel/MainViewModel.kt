package com.example.theweatherwithnesterenko.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.theweatherwithnesterenko.repository.DepositoryImpl

class MainViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: DepositoryImpl = DepositoryImpl()
) :
    ViewModel() {

    fun getData(): LiveData<AppState> {
        return liveData
    }

    fun getWeatherRussia() = getWeather(true)
    fun getWeatherWorld() = getWeather(false)

    private fun getWeather(isRussian:Boolean) {
        Thread {
            liveData.postValue(AppState.LoadingProcess)
            if (true){
                val answer = if(!isRussian) repository.getWorldWeatherFromLocalStorage()
                else repository.getRussianWeatherFromLocalStorage()
                liveData.postValue(AppState.Success(answer))
            }
            else
                liveData.postValue(AppState.FatalError(IllegalAccessException()))
        }.start()
    }

}