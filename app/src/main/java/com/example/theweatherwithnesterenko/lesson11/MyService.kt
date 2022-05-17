package com.example.theweatherwithnesterenko.lesson11

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.theweatherwithnesterenko.R
import com.example.theweatherwithnesterenko.utils.TAG
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        Log.d(TAG, "onMessageReceived: $message")
        if (!message.data.isNullOrEmpty()) {
            val title = message.data[KEY_TITLE]
            val message = message.data[KEY_MESSAGE]
            if (!title.isNullOrEmpty() && !message.isNullOrEmpty()) {
                push(title, message)
            }
        }
    }

    companion object { //todo попробовать без companion object
        private const val NOTIFICATION_ID_LOW = 1
        private const val NOTIFICATION_ID_HIGH = 2
        private const val CHANNEL_ID_LOW = "channel_low"
        private const val CHANNEL_ID_HIGH = "channel_high"
        private const val KEY_TITLE = "myTitle"
        private const val KEY_MESSAGE = "myMessage"
        //todo прикрепить server-key при сдаче ДЗ
        private const val KEY_SERVER =
            "AAAAbqWcDSI:APA91bFNc8h4Niu0qohjLMtTmsNQWxgCvZDWdrK3byzK2GnpOiV3GtuzXis-erRv-ZT7D0rIiN6kjWrubT0ZlfpSoaMUIegJ4iBK3MebetxdLXJHx3xjMCVcohA_UfaM7fACVvgXROG6"
    }

    private fun push(title: String, message: String) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        /*val notificationBuilderLow = NotificationCompat.Builder(this, CHANNEL_ID_LOW).apply {
            setSmallIcon(R.drawable.ic_map_pin)//todo change icon
            setContentTitle("getString(R.string.law_notification_title)") //todo вынести в ресурсы
            setContentText("getString(R.string.law_notification_text)") //todo вынести в ресурсы
            priority = NotificationManager.IMPORTANCE_LOW // todo полазить по константам, почитать

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelNameLow = "Name $CHANNEL_ID_LOW"
            val channelDescriptionLow = "Description $CHANNEL_ID_LOW"
            val channelPriorityLow = NotificationManager.IMPORTANCE_LOW
            val channelLow =
                NotificationChannel(CHANNEL_ID_LOW, channelNameLow, channelPriorityLow).apply {
                    description = channelDescriptionLow
                }
            notificationManager.createNotificationChannel(channelLow)
        }
        notificationManager.notify(NOTIFICATION_ID_LOW, notificationBuilderLow.build())*/

        val notificationBuilderHigh = NotificationCompat.Builder(this, CHANNEL_ID_HIGH).apply {
            setSmallIcon(R.drawable.ic_map_marker)//todo change icon
            setContentTitle(title)
            setContentText(message)
            priority = NotificationManager.IMPORTANCE_HIGH
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelNameHigh = "Name $CHANNEL_ID_HIGH"
            val channelDescriptionHigh = "Description $CHANNEL_ID_HIGH"
            val channelPriorityHigh = NotificationManager.IMPORTANCE_HIGH
            val channelHigh =
                NotificationChannel(CHANNEL_ID_HIGH, channelNameHigh, channelPriorityHigh).apply {
                    description = channelDescriptionHigh
                }
            notificationManager.createNotificationChannel(channelHigh)
        }
        notificationManager.notify(NOTIFICATION_ID_HIGH, notificationBuilderHigh.build())


    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

}