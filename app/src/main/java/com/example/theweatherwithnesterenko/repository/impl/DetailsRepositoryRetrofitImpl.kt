package com.example.theweatherwithnesterenko.repository.impl

import android.util.Log
import com.example.theweatherwithnesterenko.BuildConfig
import com.example.theweatherwithnesterenko.MainApp
import com.example.theweatherwithnesterenko.repository.weather.City
import com.example.theweatherwithnesterenko.repository.weather.WeatherAPI
import com.example.theweatherwithnesterenko.repository.dto.WeatherDTO
import com.example.theweatherwithnesterenko.repository.repo.DetailsRepository
import com.example.theweatherwithnesterenko.utils.MASTER_DOMAIN
import com.example.theweatherwithnesterenko.utils.TAG
import com.example.theweatherwithnesterenko.utils.convertDtoToModel
import com.example.theweatherwithnesterenko.viewmodel.DetailsViewModel
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailsRepositoryRetrofitImpl : DetailsRepository {

    private val weatherAPI: WeatherAPI = Retrofit.Builder().apply {
        baseUrl(MASTER_DOMAIN) //todo change to YANDEX_DOMAIN before upload
        addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
    }.build().create(WeatherAPI::class.java)

    override fun getWeatherDetails(city: City, callbackForOne: DetailsViewModel.CallbackForOne) {
       weatherAPI.getWeather(BuildConfig.WEATHER_API_KEY, city.lat, city.lon)
            .enqueue(object : Callback<WeatherDTO> {
                override fun onResponse(call: Call<WeatherDTO>, response: Response<WeatherDTO>) {
                    call.request().body()
                    if (response.isSuccessful) {
                        response.body()?.let {
                            val weather = convertDtoToModel(it)
                            weather.city = city
                            callbackForOne.onResponse(weather)
                        }
                    } else{
                        callbackForOne.onFail() //FIXME
                        Log.d(TAG, "onResponse() called with: call = $call, response = $response")
                    }
                }

                override fun onFailure(call: Call<WeatherDTO>, t: Throwable) {
                    callbackForOne.onFail() //FIXME
                    Log.d(TAG, "onFailure() called with: call = $call, t = $t")
                }
            })
    }
}