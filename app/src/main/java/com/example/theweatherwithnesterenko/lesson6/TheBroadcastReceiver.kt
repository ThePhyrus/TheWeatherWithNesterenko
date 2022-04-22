package com.example.theweatherwithnesterenko.lesson6

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.theweatherwithnesterenko.utils.KEY_SERVICE_MESSAGE
import com.example.theweatherwithnesterenko.utils.TAG

class TheBroadcastReceiver: BroadcastReceiver() { // TheBroadcastReceiver - это есть приёмник
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            val extra = it.getStringExtra(KEY_SERVICE_MESSAGE)
            Log.d(TAG, "TheBroadcastReceiver onReceive: $extra")
        }
    }
}