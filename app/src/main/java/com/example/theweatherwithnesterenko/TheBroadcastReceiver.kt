package com.example.theweatherwithnesterenko

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.theweatherwithnesterenko.utils.KEY_BUNDLE_SERVICE_MESSAGE
import com.example.theweatherwithnesterenko.utils.TAG


class TheBroadcastReceiver : BroadcastReceiver() { // TheBroadcastReceiver - это есть приёмник
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            val extra = it.getStringExtra(KEY_BUNDLE_SERVICE_MESSAGE)
            Log.d(TAG, "TheBroadcastReceiver onReceive: $extra")
        }
    }
}