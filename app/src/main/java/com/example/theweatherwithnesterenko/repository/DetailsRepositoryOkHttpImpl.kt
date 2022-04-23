package com.example.theweatherwithnesterenko.repository

import com.example.theweatherwithnesterenko.BuildConfig
import com.example.theweatherwithnesterenko.repository.dto.WeatherDTO
import com.example.theweatherwithnesterenko.utils.MASTER_DOMAIN
import com.example.theweatherwithnesterenko.utils.X_YANDEX_API_KEY
import com.example.theweatherwithnesterenko.utils.YANDEX_ENDPOINT
import com.example.theweatherwithnesterenko.utils.convertDtoToModel
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
        requestBuilder.url("$MASTER_DOMAIN${YANDEX_ENDPOINT}lat=${city.lat}&lon=${city.lat}") // адрес
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
                //todo HW
            }
        }.start()
    }
}