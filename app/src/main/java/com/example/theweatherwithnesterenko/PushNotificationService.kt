package com.example.theweatherwithnesterenko

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.theweatherwithnesterenko.utils.TAG
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushNotificationService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) { //todo попробовать сделать что-нибудь другое вместо
        Log.d(TAG, "onMessageReceived: ${message.data}")
        if (!message.data.isNullOrEmpty()) {
            val title = message.data[KEY_NOTIFICATION_TITLE]
            val message = message.data[KEY_NOTIFICATION_MESSAGE]
            if (!title.isNullOrEmpty() && !message.isNullOrEmpty()) {
                push(title, message)
            }
        }
    }

    companion object {
        private const val NOTIFICATION_ID_HIGH = 4
        private const val CHANNEL_ID_HIGH = "High priority channel"
        private const val KEY_NOTIFICATION_TITLE = "myTitle"
        private const val KEY_NOTIFICATION_MESSAGE = "myMessage"
    }

    private fun push(title: String, message: String) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationIntent = Intent(applicationContext, MainActivity::class.java)
        val contentIntent =
            PendingIntent.getActivity(
                this,
                0,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        val notificationBuilderHigh = NotificationCompat.Builder(this, CHANNEL_ID_HIGH).apply {
            setSmallIcon(R.drawable.ic_notification)
            setContentTitle(title)
            setContentIntent(contentIntent)
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

    private lateinit var clientToken:String
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        clientToken = token
        Log.d(TAG, "onNewToken() called with: token = $token")
    }

}