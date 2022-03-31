package com.example.theweatherwithnesterenko.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.theweatherwithnesterenko.repository.FacadeImpl
import com.example.theweatherwithnesterenko.viewmodel.states.AppState

class MainViewModel(
    private val lD: MutableLiveData<AppState> = MutableLiveData(),
    private val repo: FacadeImpl = FacadeImpl()
) :
    ViewModel() {

    fun getData(): LiveData<AppState> {
        return lD
    }

    fun getWeatherRussia() = getWeather(true)
    fun getWeatherWorld() = getWeather(false)

    private fun getWeather(isRussian:Boolean) {
        Thread {
            lD.postValue(AppState.LoadingProcess)
            if (true){
                val answer = if(!isRussian) repo.getWeatherDataFromLocalStorageWorld()
                else repo.getWeatherDataFromLocalStorageRussia()
                lD.postValue(AppState.Success(answer))
            }
            else
                lD.postValue(AppState.FatalError(IllegalAccessException()))
        }.start()
    }

}