package com.example.theweatherwithnesterenko.view.details

import android.app.IntentService
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.theweatherwithnesterenko.BuildConfig
import com.example.theweatherwithnesterenko.repository.dto.WeatherDTO
import com.example.theweatherwithnesterenko.utils.*
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class DetailsService(val name: String = "") : IntentService(name) {
    override fun onHandleIntent(intent: Intent?) {
        Log.d(TAG, "DetailsService started")
        intent?.let {
            val lat = it.getDoubleExtra(KEY_BUNDLE_LAT, 0.0)
            val lon = it.getDoubleExtra(KEY_BUNDLE_LON, 0.0)
            val urlText = "$MASTER_DOMAIN${YANDEX_ENDPOINT}$LATITUDE=$lat&$LONGITUDE=$lon"
            val uri = URL(urlText)
            val urlConnection: HttpURLConnection =
                (uri.openConnection() as HttpURLConnection).apply {
                    connectTimeout = 1000
                    readTimeout = 1000
                    addRequestProperty(X_YANDEX_API_KEY, BuildConfig.WEATHER_API_KEY)
                }
           try {
               val responseCode = urlConnection.responseCode
               val responseMessage = urlConnection.responseMessage
               val serverside = 500..599
               val clientside = 400..499
               val responseOk = 200..299
               val unknownSide = 0..199

               when(responseCode){
                   in responseOk -> {
                       Log.d(TAG, "onHandleIntent: $responseMessage $responseCode")
                       val buffer = BufferedReader(InputStreamReader(urlConnection.inputStream))
                       val weatherDTO: WeatherDTO = Gson().fromJson(buffer, WeatherDTO::class.java)
                       val message = Intent(KEY_WAVE_SERVICE_BROADCAST)
                       message.putExtra(KEY_BUNDLE_SERVICE_BROADCAST_WEATHER, weatherDTO)
                       LocalBroadcastManager.getInstance(this).sendBroadcast(message)
                   }
                   in clientside -> {
                       //todo разобраться с callback в другом классе
                       Log.d(TAG, "onHandleIntent: $responseMessage $responseCode")
                   }
                   in serverside -> {
                       //FIXME не могу разобраться с callback
                       Log.d(TAG, "onHandleIntent: $responseMessage $responseCode")
                   }
                   in unknownSide -> {
                       //FIXME не могу разобраться с callback
                       Log.d(TAG, "onHandleIntent: $responseMessage $responseCode")
                   }
                   else -> {
                       //FIXME не могу разобраться с callback
                       Log.d(TAG, "onHandleIntent: Что-то не так с when??")
                   }
               }
           } catch (e: JsonSyntaxException){
               // ловим ошибки любезно предоставленные автогенерацией Джейсонов
               Log.d(TAG, "onHandleIntent: $e")
           } finally {
               urlConnection.disconnect()
           }
        }
        Log.d(TAG, "DetailsService finished")
    }
}
