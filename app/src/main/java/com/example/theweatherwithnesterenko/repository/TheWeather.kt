package com.example.theweatherwithnesterenko.repository

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class TheWeather(
    val city: City = getDefaultCity(),
    val temperature: Int = 0,
    val feelsLike: Int = 0
) : Parcelable

fun getDefaultCity() = City("saint-Petersburg", 59.939099, 30.315877)

@Parcelize
data class City(val name: String, val lat: Double, val lon: Double) : Parcelable

fun getWorldCities(): List<TheWeather> {
    return listOf(
        TheWeather(City("London", 51.507357, -0.127696), 0, 0),
        TheWeather(City("Tokyo", 35.681729, 139.753927), 0, 0),
        TheWeather(City("Paris", 48.856663, 2.351556), 0, 0),
        TheWeather(City("Berlin", 52.518621, 13.375142), 0, 0),
        TheWeather(City("Rome", 41.902695, 12.496176), 0, 0),
        TheWeather(City("Minsk", 53.902284, 27.561831), 0, 0),
        TheWeather(City("Istanbul", 41.011218, 28.987178), 0, 0),
        TheWeather(City("Washington", 38.899513, -77.036536), 0, 0),
        TheWeather(City("Kiev", 50.450441, 30.523550), 0, 0),
        TheWeather(City("Beijing", 39.901850, 116.391441), 0, 0)
    )
}

fun getRussianCities(): List<TheWeather> {
    return listOf(
        TheWeather(City("Moscow", 55.755819, 37.617644), 0, 0),
        TheWeather(City("Saint-Petersburg", 59.939099, 30.315877), 0, 0),
        TheWeather(City("Novosibirsk", 55.030204, 82.920430), 0, 0),
        TheWeather(City("Ekaterinburg", 56.838011, 60.597474), 0, 0),
        TheWeather(City("Nizhniy Novgorod", 56.326797, 44.006516), 0, 0),
        TheWeather(City("Kazan", 55.796127, 49.106414), 0, 0),
        TheWeather(City("Chelyabinsk", 55.159902, 61.402554), 0, 0),
        TheWeather(City("Omsk", 54.989347, 73.368221), 0, 0),
        TheWeather(City("Rostov-on-Don", 47.222078, 39.720358), 0, 0),
        TheWeather(City("Ufa", 54.735152, 55.958736), 0, 0)
    )
}