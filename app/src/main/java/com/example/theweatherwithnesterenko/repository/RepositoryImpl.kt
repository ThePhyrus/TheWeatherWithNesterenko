package com.example.theweatherwithnesterenko.repository


class RepositoryImpl : Repository {
    override fun getWeatherFromServer(): Weather {
        Thread.sleep(5000L) // эмуляция сетевого запроса
        return Weather()// эмуляция ответа
    }

    override fun getWeatherFromLocalStorage(): Weather {
        Thread.sleep(2000L) // эмуляция запроса локального
        return Weather()// эмуляция ответа
    }
}