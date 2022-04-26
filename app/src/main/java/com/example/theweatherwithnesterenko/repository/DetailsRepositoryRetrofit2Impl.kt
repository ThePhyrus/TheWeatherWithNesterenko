package com.example.theweatherwithnesterenko.repository

import com.example.theweatherwithnesterenko.BuildConfig
import com.example.theweatherwithnesterenko.repository.dto.WeatherDTO
import com.example.theweatherwithnesterenko.utils.MASTER_DOMAIN
import com.example.theweatherwithnesterenko.utils.YANDEX_DOMAIN
import com.example.theweatherwithnesterenko.utils.convertDtoToModel
import com.example.theweatherwithnesterenko.viewmodel.DetailsViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class DetailsRepositoryRetrofit2Impl : DetailsRepository {
    override fun getWeatherDetails(city: City, callbackMy: DetailsViewModel.Callback) {
        val weatherAPI = Retrofit.Builder().apply {
            baseUrl(MASTER_DOMAIN)
            addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        }.build().create(WeatherAPI::class.java)

//       val response =  weatherAPI.getWeather(BuildConfig.WEATHER_API_KEY, city.lat, city.lon).execute() // можно так (синхронно)
        weatherAPI.getWeather(BuildConfig.WEATHER_API_KEY, city.lat, city.lon)
            .enqueue(object : Callback<WeatherDTO> {
                override fun onResponse(call: Call<WeatherDTO>, response: Response<WeatherDTO>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            callbackMy.onResponse(convertDtoToModel(it))
                        }
                    } else{
                        callbackMy.onFail() //??? HW
                    }
                }

                override fun onFailure(call: Call<WeatherDTO>, t: Throwable) {
                    callbackMy.onFail() //??? HW
                }
            })
    }
}