package com.example.theweatherwithnesterenko.lesson6

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.example.theweatherwithnesterenko.utils.*
import java.lang.Thread.sleep

class MainService(val name:String =""):IntentService(name) {
    override fun onHandleIntent(intent: Intent?) {
        Log.d(TAG, "MainService has been created")
        sleep(SLEEP_3000)
        intent?.let {
            val extra = it.getStringExtra(KEY_ACTIVITY_MESSAGE) // сервис получает привет из активити
            Log.d(TAG, "onHandleIntent: $extra")
            sleep(SLEEP_2000) // пару секунд Сервис "пишет" ответ
                // создам "конверт" (val message)
            val message = Intent("theaction") // todo в константу
                // вложу в него письмо с ответом на сообщение от Сервиса
            message.putExtra(KEY_SERVICE_MESSAGE, "Привет, Активити! Как дела?")
            // отправлю этот конверт
            sendBroadcast(message) // на глобальной волне
//            LocalBroadcastManager.getInstance(this).sendBroadcast(message) // локально

        }

    }
}