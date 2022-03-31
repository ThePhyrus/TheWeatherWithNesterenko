package com.example.theweatherwithnesterenko.repository

class FacadeImpl:Facade {
    override fun getWeatherDataFromServer():Weather {
        Thread.sleep(2000L) // эмуляция сетевого запроса
        return Weather()// эмуляция ответа
    }

    override fun getWeatherDataFromLocalStorageWorld():List<Weather> {
        Thread.sleep(2000L) // эмуляция запроса
        return getWorldCities()// эмуляция ответа
    }
    override fun getWeatherDataFromLocalStorageRussia():List<Weather> {
        Thread.sleep(2000L) // эмуляция запроса
        return getRussianCities() // эмуляция ответа
    }
}