package com.example.theweatherwithnesterenko.repository.impl

import com.example.theweatherwithnesterenko.BuildConfig
import com.example.theweatherwithnesterenko.repository.weather.City
import com.example.theweatherwithnesterenko.repository.weather.WeatherAPI
import com.example.theweatherwithnesterenko.repository.dto.WeatherDTO
import com.example.theweatherwithnesterenko.repository.repo.DetailsRepositoryOne
import com.example.theweatherwithnesterenko.utils.MASTER_DOMAIN
import com.example.theweatherwithnesterenko.utils.convertDtoToModel
import com.example.theweatherwithnesterenko.viewmodel.DetailsViewModel
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailsRepositoryRetrofit2Impl : DetailsRepositoryOne {
    override fun getWeatherDetails(city: City, callbackMy: DetailsViewModel.Callback) {
        val weatherAPI = Retrofit.Builder().apply {
            baseUrl(MASTER_DOMAIN)
            addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        }.build().create(WeatherAPI::class.java)

        weatherAPI.getWeather(BuildConfig.WEATHER_API_KEY, city.lat, city.lon)
            .enqueue(object : Callback<WeatherDTO> {
                override fun onResponse(call: Call<WeatherDTO>, response: Response<WeatherDTO>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            val weather = convertDtoToModel(it)
                            weather.city = city
                            callbackMy.onResponse(weather)
                        }
                    } else{
                        callbackMy.onFail() //FIXME
                    }
                }

                override fun onFailure(call: Call<WeatherDTO>, t: Throwable) {
                    callbackMy.onFail() //FIXME
                }
            })
    }
}