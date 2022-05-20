package com.example.theweatherwithnesterenko.utils

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class Funs {
}

fun mainFunction() {
    Log.d(TAG, "mainFunction() called")
}

fun catchToken() {
    FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
        if (!task.isSuccessful) {
            Log.w(TAG, "Fetching FCM registration token failed", task.exception)
            return@OnCompleteListener
        }
        val token = task.result
        Log.d(TAG, "catchToken() called with: task = $token")
    })
}