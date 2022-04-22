package com.example.theweatherwithnesterenko.view.details

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.example.theweatherwithnesterenko.utils.*

class DetailsService(val name: String = "") : IntentService(name) {
    override fun onHandleIntent(intent: Intent?) {
        Log.d(TAG, "DetailsService works")
        Thread.sleep(TIME_THREE_SECONDS) //todo delete sleep?
        intent?.let {
            val lat = it.getStringExtra(KEY_BUNDLE_LAT)
            val lon = it.getStringExtra(KEY_BUNDLE_LON)
            Log.d(TAG, "DetailsService works: $lat $lon")
            Thread.sleep(TIME_THREE_SECONDS) //todo delete sleep?

            // request on Yandex


            val message =
                Intent(KEY_WAVE_THE_ACTION)

            message.putExtra(KEY_BUNDLE_SERVICE_MESSAGE, "Привет, Активити! Как дела?")

            sendBroadcast(message) // на глобальной волне
//            LocalBroadcastManager.getInstance(this).sendBroadcast(message) // локально
        }
    }
}
