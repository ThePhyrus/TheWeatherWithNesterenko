package com.example.theweatherwithnesterenko.lesson6

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.example.theweatherwithnesterenko.utils.*
import java.lang.Thread.sleep

class MainService(val name: String = "") : IntentService(name) {

    override fun onHandleIntent(intent: Intent?) {
        Log.d(TAG, "MainService has been created")
        sleep(TIME_THREE_SECONDS) // Сервис ждёт новых друзей
        intent?.let {
            val extra =
                it.getStringExtra(KEY_BUNDLE_ACTIVITY_MESSAGE) // сервис получает привет из активити
            Log.d(TAG, "onHandleIntent: $extra")
            sleep(TIME_THREE_SECONDS) // пару секунд Сервис "пишет" ответ
            // создам "конверт" (val message)
            val message =
                Intent(KEY_WAVE_THE_ACTION) //FIXME кажется неправильно сделал(( В манифесте константу не вижу
            // вложу в него письмо с ответом на сообщение от Сервиса
            message.putExtra(KEY_BUNDLE_SERVICE_MESSAGE, "Привет, Активити! Как дела?")
            // отправлю этот конверт
            sendBroadcast(message) // на глобальной волне
//            LocalBroadcastManager.getInstance(this).sendBroadcast(message) // локально
        }
    }
}