package com.example.theweatherwithnesterenko.lesson6

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.example.theweatherwithnesterenko.R
import com.example.theweatherwithnesterenko.utils.*
import java.lang.Thread.sleep

class MainService(val name: String = "") : IntentService(name) {

    override fun onHandleIntent(intent: Intent?) {
        Log.d(TAG, "MainService has been created")
        sleep(TIME_THREE_SECONDS)
        intent?.let {
            val extra =
                it.getStringExtra(KEY_BUNDLE_ACTIVITY_MESSAGE) // сервис получает привет из активити
            Log.d(TAG, "onHandleIntent: $extra")
            sleep(TIME_THREE_SECONDS) // пару секунд Сервис "пишет" ответ
            // создам "конверт" (val message)
            val message =
                Intent(KEY_WAVE_THE_ACTION) //FIXME эта константа должна быть в strings.xml и её же прописать в манифесте. Так?
            // вложу в него письмо с ответом на сообщение от Сервиса
            message.putExtra(KEY_BUNDLE_SERVICE_MESSAGE, resources.getString(R.string.hallo_from_service))
            // отправлю этот конверт
            sendBroadcast(message) // на глобальной волне
//            LocalBroadcastManager.getInstance(this).sendBroadcast(message) // локально
        }
    }
}