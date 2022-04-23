package com.example.theweatherwithnesterenko.repository

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.theweatherwithnesterenko.BuildConfig
import com.example.theweatherwithnesterenko.repository.dto.WeatherDTO
import com.example.theweatherwithnesterenko.utils.*
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class WeatherLoader(
    private val onServerResponseListener: OnServerResponse,
    private val onErrorListener: OnServerResponseListener
) {

    fun loadWeather(lat: Double, lon: Double) {
//        val urlText = "$YANDEX_DOMAIN${YANDEX_PATH}lat=$lat&lon=$lon" // original yandex domain
        val urlText = "$MASTER_DOMAIN${YANDEX_ENDPOINT}lat=$lat&lon=$lon"
        val uri = URL(urlText)
        Thread { // if use original yandex domain, then connection need to be changed to https
            val urlConnection: HttpURLConnection =
                (uri.openConnection() as HttpURLConnection).apply {
                    connectTimeout = 1000
                    readTimeout = 1000
                    addRequestProperty(X_YANDEX_API_KEY, BuildConfig.WEATHER_API_KEY)
                }
            try {
                val headers = urlConnection.headerFields
                val responseCode = urlConnection.responseCode
                val responseMessage = urlConnection.responseMessage
                val serverside = 500..100000 //FIXME хде предел?
                val clientside = 400..499
                val responseOk = 200..299
                val unknownSide = 0..199

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
                        //FIXME не могу разобраться с callback
                        Log.d(TAG, "loadWeather: $responseMessage $responseCode")
                        // не могу пробросить callback  во фрагмент, чтобы показать SnackBar
                    }
                    in serverside -> {
                        ///FIXME не могу разобраться с callback
                        Log.d(TAG, "loadWeather: $responseMessage $responseCode")
                        //не могу пробросить callback  во фрагмент, чтобы показать SnackBar
                    }
                    in unknownSide -> {
                        //FIXME не могу разобраться с callback
                        Log.d(TAG, "loadWeather: $responseMessage $responseCode")
                    }
                }
            } catch (e: JsonSyntaxException) {
                // ловим ошибки любезно предоставленные автогенерацией Джейсонов
                //FIXME не могу разобраться с callback
                Log.d(TAG, "loadWeather: $e")
            } finally {
                urlConnection.disconnect()
            }
        }.start()
    }
}