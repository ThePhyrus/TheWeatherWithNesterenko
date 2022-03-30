package com.example.theweatherwithnesterenko.repository

class DepositoryImpl:Depository {
    override fun getWeatherFromServer():TheWeather {
        Thread.sleep(2000L) // эмуляция сетевого запроса
        return TheWeather()// эмуляция ответа
    }

    override fun getWorldWeatherFromLocalStorage():List<TheWeather> {
        return getWorldCities()// эмуляция ответа
    }
    override fun getRussianWeatherFromLocalStorage():List<TheWeather> {
        return getRussianCities() // эмуляция ответа
    }
}