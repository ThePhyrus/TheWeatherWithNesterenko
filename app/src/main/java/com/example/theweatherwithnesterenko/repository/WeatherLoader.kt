package com.example.theweatherwithnesterenko.repository

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.theweatherwithnesterenko.BuildConfig
import com.example.theweatherwithnesterenko.utils.KEY_X_YANDEX_API
import com.example.theweatherwithnesterenko.utils.TAG
import com.example.theweatherwithnesterenko.viewmodel.ResponseState
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
                addRequestProperty(KEY_X_YANDEX_API, BuildConfig.WEATHER_API_KEY)
            }
        Thread {
            val headers = urlConnection.headerFields
            val responseCode = urlConnection.responseCode
            val responseMessage = urlConnection.responseMessage
            val serverside = 500..100000 //FIXME хде предел?
            val clientside = 400..499
            val responseOk = 0..299

            when (responseCode) {
                in responseOk -> {
                    val buffer = BufferedReader(InputStreamReader(urlConnection.inputStream))
                    val weatherDTO: WeatherDTO = Gson().fromJson(buffer, WeatherDTO::class.java)

                    Handler(Looper.getMainLooper()).post {
                        onServerResponseListener.onResponse(weatherDTO)
                    }

                    Log.d(TAG, "loadWeather: $responseMessage $responseCode")
                }
                in clientside -> {
                    //todo обработать ошибку, которая случается на стороне клиента
                    Log.d(TAG, "loadWeather: $responseMessage $responseCode")
                    // не могу пробросить callback  во фрагмент, чтобы показать SnackBar
                }
                in serverside -> {
                    //todo обработать ошибку, которая случается на стороне сервера
                    Log.d(TAG, "loadWeather: $responseMessage $responseCode")
                    //не могу пробросить callback  во фрагмент, чтобы показать SnackBar
                }
            }
            urlConnection.disconnect()
            //FIXME везде пишут про try-catch-finally.
            // Можно ли закрывать соединение просто вот так, не в блоке finally?
        }.start()
    }
}