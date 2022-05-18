package com.example.theweatherwithnesterenko.repository.impl

import com.example.theweatherwithnesterenko.repository.Repository
import com.example.theweatherwithnesterenko.repository.weather.Weather
import com.example.theweatherwithnesterenko.repository.weather.getRussianCities
import com.example.theweatherwithnesterenko.repository.weather.getWorldCities


class RepositoryImpl : Repository {
    override fun getWeatherFromServer(): Weather {
        Thread.sleep(2000L) // эмуляция сетевого запроса
        return Weather()// эмуляция ответа
    }

    override fun getWorldWeatherFromLocalStorage() = getWorldCities()// эмуляция ответа

    override fun getRussianWeatherFromLocalStorage() = getRussianCities() // эмуляция ответа
}