package com.example.theweatherwithnesterenko.repository


class RepositoryImpl:Repository {
    override fun getWeatherFromServer():Weather {
        Thread.sleep(2000L) // эмуляция сетевого запроса
        return Weather()// эмуляция ответа
    }

    override fun getWorldWeatherFromLocalStorage()= getWorldCities()// эмуляция ответа

    override fun getRussianWeatherFromLocalStorage()= getRussianCities() // эмуляция ответа
}