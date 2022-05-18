package com.example.theweatherwithnesterenko.utils

import com.example.theweatherwithnesterenko.domain.room.HistoryEntity
import com.example.theweatherwithnesterenko.repository.weather.City
import com.example.theweatherwithnesterenko.repository.weather.Weather
import com.example.theweatherwithnesterenko.repository.dto.FactDTO
import com.example.theweatherwithnesterenko.repository.dto.WeatherDTO
import com.example.theweatherwithnesterenko.repository.weather.getDefaultCity

private const val KEY_SERVER = "AAAAbqWcDSI:APA91bFNc8h4Niu0qohjLMtTmsNQWxgCvZDWdrK3byzK2GnpOiV3GtuzXis-erRv-ZT7D0rIiN6kjWrubT0ZlfpSoaMUIegJ4iBK3MebetxdLXJHx3xjMCVcohA_UfaM7fACVvgXROG6"
const val TAG: String = "@@@"
const val KEY_BUNDLE_WEATHER_FROM_LIST_TO_DETAILS: String = "weather"
const val KEY_BUNDLE_SERVICE_BROADCAST_WEATHER: String = "weather_service_broadcast"
const val KEY_WAVE_SERVICE_BROADCAST: String = "theaction_way"
const val X_YANDEX_API_KEY: String = "X-Yandex-API-Key"
const val YANDEX_DOMAIN: String = "https://api.weather.yandex.ru"
const val MASTER_DOMAIN: String = "http://212.86.114.27/"
const val YANDEX_ENDPOINT: String = "v2/informers?"
const val YASTATIC_DOMAIN: String = "https://yastatic.net/"
const val YANDEX_WEATHER_ICON_ENDPOINT: String = "weather/i/icons/blueye/color/svg/"
const val FREEPNGIMG_DOMAIN:String = "https://freepngimg.com/"
const val FREEPNGIMG_ENDPOINT:String = "thumb/city/36275-3-city-hd.png"
const val LATITUDE = "lat"
const val LONGITUDE = "lon"
const val KEY_BUNDLE_LAT: String = "lat1"
const val KEY_BUNDLE_LON: String = "lon1"
//
const val KEY_SP_FILE_NAME_1: String = "fileName1"
const val KEY_SP_FILE_NAME_1_KEY_IS_RUSSIAN: String = "is_russian"

//
const val DOT_SVG:String = ".svg"
//
const val ACTION_AIRPLANE_MODE:String = "android.intent.action.AIRPLANE_MODE"
// keys for service training
const val KEY_BUNDLE_ACTIVITY_MESSAGE: String = "activity_message"
const val KEY_BUNDLE_SERVICE_MESSAGE: String = "service_message"
// actions
const val KEY_WAVE_THE_ACTION: String = "theaction" // как-то так?
//Room
const val KEY_HISTORY_TABLE :String= "history_table" //todo в строковые xml?
//Permission request codes
const val REQUEST_CODE_FOR_PERMISSION_TO_READ_USER_CONTACTS:Int = 101
const val REQUEST_CODE_FOR_PERMISSION_TO_ACCESS_FINE_LOCATION:Int = 100

class Utils {

}

fun convertDtoToModel(weatherDTO: WeatherDTO): Weather {
    val fact: FactDTO = weatherDTO.factDTO
    return (Weather(getDefaultCity(), fact.temperature, fact.feelsLike, fact.icon))
}


fun convertHistoryEntityToWeather(entityList: List<HistoryEntity>): List<Weather> {
    return entityList.map {
        Weather(City(it.city,0.0,0.0), it.temperature, it.feelsLike, it.icon)
    }
}

fun convertWeatherToEntity(weather: Weather): HistoryEntity {
    return HistoryEntity(0, weather.city.name, weather.temperature,weather.feelsLike,weather.icon)
}




