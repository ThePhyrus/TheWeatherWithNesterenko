package com.example.theweatherwithnesterenko.repository.impl

import com.example.theweatherwithnesterenko.repository.repo.Repository
import com.example.theweatherwithnesterenko.repository.weather.Weather
import com.example.theweatherwithnesterenko.repository.weather.getRussianCities
import com.example.theweatherwithnesterenko.repository.weather.getWorldCities


class RepositoryImpl : Repository {

    override fun getWorldWeatherFromLocalStorage() = getWorldCities()// эмуляция ответа

    override fun getRussianWeatherFromLocalStorage() = getRussianCities() // эмуляция ответа
}