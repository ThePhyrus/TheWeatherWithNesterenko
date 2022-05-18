package com.example.theweatherwithnesterenko.repository.weather

import com.example.theweatherwithnesterenko.repository.dto.WeatherDTO
import com.example.theweatherwithnesterenko.utils.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface WeatherAPI {
    @GET(YANDEX_ENDPOINT) // endpoint only
    fun getWeather(
        @Header(X_YANDEX_API_KEY) apikey: String,
        @Query(LATITUDE) lat: Double,
        @Query(LONGITUDE) lon: Double
    ): Call<WeatherDTO>









    //todo try getWeather2
    @GET(YANDEX_ENDPOINT2) // заготовочка на случай, если возьму тариф тестовый
    fun getWeather2(
        @Header(X_YANDEX_API_KEY) apikey: String,
        @Query(LATITUDE) lat: Double,
        @Query(LONGITUDE) lon: Double
    ): Call<WeatherDTO>
}