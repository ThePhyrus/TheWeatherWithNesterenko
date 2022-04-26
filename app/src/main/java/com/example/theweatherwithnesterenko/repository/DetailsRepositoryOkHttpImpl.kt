package com.example.theweatherwithnesterenko.repository

import android.util.Log
import com.example.theweatherwithnesterenko.BuildConfig
import com.example.theweatherwithnesterenko.repository.dto.WeatherDTO
import com.example.theweatherwithnesterenko.utils.*
import com.example.theweatherwithnesterenko.viewmodel.DetailsViewModel
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

class DetailsRepositoryOkHttpImpl: DetailsRepository {
    override fun getWeatherDetails(city: City, callback: DetailsViewModel.Callback) {
        val client = OkHttpClient() // создал клиент
        val requestBuilder = Request.Builder() // создал "строителя" запросов на сервер
        //ZAPROS
        requestBuilder.addHeader(X_YANDEX_API_KEY, BuildConfig.WEATHER_API_KEY) // заголовок
        requestBuilder.url("$MASTER_DOMAIN${YANDEX_ENDPOINT}$LATITUDE=${city.lat}&$LONGITUDE=${city.lat}") // адрес
        val request = requestBuilder.build() // создали запрос
        val call = client.newCall(request) // создали звоночек
        //ZAPROS
        Thread{
            val response = call.execute() // синхронный запрос
            if (response.isSuccessful){
                val serverResponse = response.body()!!.string()
                val weatherDTO: WeatherDTO = Gson().fromJson(serverResponse, WeatherDTO::class.java)
                val weather = convertDtoToModel(weatherDTO)
                weather.city = city
                callback.onResponse(weather)
            }else{
                Log.d(TAG, "getWeatherDetails: some error")
            }
        }.start()
    }
}