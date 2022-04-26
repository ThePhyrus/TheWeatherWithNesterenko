package com.example.theweatherwithnesterenko.utils

import com.example.theweatherwithnesterenko.repository.Weather
import com.example.theweatherwithnesterenko.repository.dto.FactDTO
import com.example.theweatherwithnesterenko.repository.dto.WeatherDTO
import com.example.theweatherwithnesterenko.repository.getDefaultCity


const val TAG: String = "@@@"
const val KEY_BUNDLE_WEATHER_FROM_LIST_TO_DETAILS: String = "weather"
const val KEY_BUNDLE_SERVICE_BROADCAST_WEATHER: String = "weather_service_broadcast"
const val KEY_WAVE_SERVICE_BROADCAST: String = "theaction_way"
const val X_YANDEX_API_KEY: String = "X-Yandex-API-Key"
const val YANDEX_DOMAIN: String = "https://api.weather.yandex.ru"
const val MASTER_DOMAIN: String = "http://212.86.114.27/"
const val YANDEX_ENDPOINT: String = "v2/informers?"
const val YANDEX_ENDPOINT2: String = "заготовочка на случай, если возьму тариф тестовый"
const val FREEPNGIMG_DOMAIN:String = "https://freepngimg.com/"
const val FREEPNGIMG_ENDPOINT:String = "thumb/city/36275-3-city-hd.png"
const val LATITUDE = "lat"
const val LONGITUDE = "lon"
const val KEY_BUNDLE_LAT: String = "lat1"
const val KEY_BUNDLE_LON: String = "lon1"

// keys for service training
const val KEY_BUNDLE_ACTIVITY_MESSAGE: String = "activity_message"
const val KEY_BUNDLE_SERVICE_MESSAGE: String = "service_message"

// actions
const val KEY_WAVE_THE_ACTION: String = "theaction" // как-то так?

// sleep time
const val TIME_ONE_SECOND: Long = 1000L
const val TIME_TWO_SECONDS: Long = 2000L
const val TIME_THREE_SECONDS: Long = 3000L

class Utils {

}

fun convertDtoToModel(weatherDTO: WeatherDTO): Weather {
    val fact: FactDTO = weatherDTO.factDTO
    return (Weather(getDefaultCity(), fact.temperature, fact.feelsLike, fact.icon))
}