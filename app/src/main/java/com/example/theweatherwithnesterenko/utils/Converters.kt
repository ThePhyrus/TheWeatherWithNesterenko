package com.example.theweatherwithnesterenko.utils

import com.example.theweatherwithnesterenko.domain.room.HistoryEntity
import com.example.theweatherwithnesterenko.repository.dto.FactDTO
import com.example.theweatherwithnesterenko.repository.dto.WeatherDTO
import com.example.theweatherwithnesterenko.repository.weather.City
import com.example.theweatherwithnesterenko.repository.weather.Weather
import com.example.theweatherwithnesterenko.repository.weather.getDefaultCity

class Converters {
}

fun convertDtoToModel(weatherDTO: WeatherDTO): Weather {
    val fact: FactDTO = weatherDTO.factDTO
    return (Weather(getDefaultCity(), fact.temperature, fact.feelsLike, fact.icon))
}


fun convertHistoryEntityToWeather(entityList: List<HistoryEntity>): List<Weather> {
    return entityList.map { //todo Научиться хранить в базе данных lat, lon
        Weather(City(it.city,0.0,0.0), it.temperature, it.feelsLike, it.icon)
    }
}

fun convertWeatherToEntity(weather: Weather): HistoryEntity {
    return HistoryEntity(0, weather.city.name, weather.temperature,weather.feelsLike,weather.icon)
}


