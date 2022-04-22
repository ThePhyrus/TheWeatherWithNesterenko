package com.example.theweatherwithnesterenko.lesson6

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.example.theweatherwithnesterenko.utils.TAG

class MainService(val name:String =""):IntentService(name) {
    override fun onHandleIntent(intent: Intent?) {
        Log.d(TAG, "onHandleIntent: work MainService")
    }
}