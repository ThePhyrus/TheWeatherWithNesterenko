package com.example.theweatherwithnesterenko.lesson11

import android.util.Log
import com.example.theweatherwithnesterenko.utils.TAG
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyService: FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        Log.d(TAG, "onMessageReceived: $message")
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

}