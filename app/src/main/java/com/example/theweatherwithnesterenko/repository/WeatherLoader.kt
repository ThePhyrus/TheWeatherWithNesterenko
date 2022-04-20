package com.example.theweatherwithnesterenko.repository

import android.os.Handler
import android.os.Looper
import com.example.theweatherwithnesterenko.BuildConfig
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class WeatherLoader(
    private val onServerResponseListener: OnServerResponse,
    private val onErrorListener: OnServerResponseListener
) {
    fun loadWeather(lat: Double, lon: Double) {

//        val urlText = "https://api.weather.yandex.ru/v2/informers?lat=$lat&lon=$lon"
        val urlText = "http://212.86.114.27/v2/informers?lat=$lat&lon=$lon"
        val uri = URL(urlText)
        val urlConnection: HttpURLConnection =
            (uri.openConnection() as HttpURLConnection).apply {
                connectTimeout = 1000
                readTimeout = 1000
                addRequestProperty("X-Yandex-API-Key", BuildConfig.WEATHER_API_KEY)
            }
        Thread {
            val headers = urlConnection.headerFields
            val responseCode = urlConnection.responseCode
            val responseMessage = urlConnection.responseMessage
            val serverside = 500..1000000
            val clientside = 400..499
            val responseOk = 0..299

            if (responseCode in serverside) {
                //todo snackbar ошибка на стороне сервера
                //        onErrorListener.onError(AppError.Error1) // поток не тот
            } else if (responseCode in clientside) {
                //todo snackbar ошибка на стороне клиента
            } else if (responseCode in responseOk) {
                val buffer = BufferedReader(InputStreamReader(urlConnection.inputStream))
                val weatherDTO: WeatherDTO = Gson().fromJson(buffer, WeatherDTO::class.java)
                Handler(Looper.getMainLooper()).post {
                    onServerResponseListener.onResponse(weatherDTO)
                }
            }
            // TODO  HW disconnect() finally?
        }.start()
    }
}