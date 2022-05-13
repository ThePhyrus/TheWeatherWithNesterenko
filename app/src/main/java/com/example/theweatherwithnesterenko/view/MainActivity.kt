package com.example.theweatherwithnesterenko.view

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.theweatherwithnesterenko.MyApp
import com.example.theweatherwithnesterenko.R
import com.example.theweatherwithnesterenko.lesson10.MapsFragment
import com.example.theweatherwithnesterenko.lesson6.MainService
import com.example.theweatherwithnesterenko.lesson6.TheBroadcastReceiver
import com.example.theweatherwithnesterenko.lesson6.ThreadFragment
import com.example.theweatherwithnesterenko.lesson9.WorkWithContentProviderFragment
import com.example.theweatherwithnesterenko.utils.*
import com.example.theweatherwithnesterenko.view.historylist.HistoryWeatherListFragment
import com.example.theweatherwithnesterenko.view.weatherlist.WeatherListFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

//TODO выводить проверки, вызвать картинку, shared preferences (сохранить настройки приложения

class MainActivity : AppCompatActivity() { //todo разобрать бардак в этом классе
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, WeatherListFragment.newInstance()).commit()
        }

        startService(Intent(this, MainService::class.java).apply {
            putExtra(KEY_BUNDLE_ACTIVITY_MESSAGE, resources.getString(R.string.hallo_from_activity))
        })

        val theReceiver = TheBroadcastReceiver() // создаётся ресивер (приёмник)
        registerReceiver(
            theReceiver,
            IntentFilter(KEY_WAVE_THE_ACTION)
        ) // регистрация ресивера на голбальной волне
        registerReceiver(
            theReceiver,
            IntentFilter(ACTION_AIRPLANE_MODE)
        ) // регистрация ресивера на голбальной волне
//        LocalBroadcastManager.getInstance(this).registerReceiver(theReceiver, IntentFilter(KEY_WAVE_THE_ACTION)) // регистрация локальная

        val sp = getSharedPreferences(KEY_SP_FILE_NAME_1, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putBoolean(KEY_SP_FILE_NAME_1_KEY_IS_RUSSIAN, true)
        editor.apply()

        val spMy = getSharedPreferences(KEY_SP_MY_FILE_1, Context.MODE_PRIVATE)
        val editorMy = spMy.edit()
        editorMy.putInt(KEY_SP_MY_FILE_1_KEY_INT, 5)
        editorMy.apply()

        val spString = getSharedPreferences(KEY_SP_MY_FILE_2, Context.MODE_PRIVATE)
        val spEditor = spString.edit()
        spEditor.putString(KEY_SP_MY_FILE_2_KEY_STRING, "string")
        spEditor.apply()


        val defaultValueIsRussian = true
        sp.getBoolean(KEY_SP_FILE_NAME_1_KEY_IS_RUSSIAN, defaultValueIsRussian)

        val spFloat = getSharedPreferences(KEY_SP_MY_FILE_3, Context.MODE_PRIVATE)
        val spEditorFloat = spFloat.edit()
        spEditorFloat.putFloat(KEY_SP_MY_FILE_4_KEY_FLOAT, 0.0f)
        spEditor.apply()


        val spFloatDefValue = 0.0f
        spFloat.getFloat(KEY_SP_MY_FILE_4_KEY_FLOAT, spFloatDefValue)


        Thread{ //todo try 1 variant
            MyApp.getHistoryDao().getAll()
        }.start() //todo try 1 variant


        push()


        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("mylogs_push", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            val token = task.result
            Log.d("mylogs_push", "$token")
        })

    }

    companion object {
        private const val NOTIFICATION_ID_LOW = 1
        private const val NOTIFICATION_ID_HIGH = 2
        private const val CHANNEL_ID_LOW = "channel_low"
        private const val CHANNEL_ID_HIGH = "channel_high"
    }

    private fun push() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationBuilderLow = NotificationCompat.Builder(this, CHANNEL_ID_LOW).apply {
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
        notificationManager.notify(NOTIFICATION_ID_LOW, notificationBuilderLow.build())

        val notificationBuilderHigh = NotificationCompat.Builder(this, CHANNEL_ID_HIGH).apply {
            setSmallIcon(R.drawable.ic_map_marker)//todo change icon
            setContentTitle("getString(R.string.high_notification_title)") //todo вынести в ресурсы
            setContentText("getString(R.string.high_notification_text)") //todo вынести в ресурсы
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


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu1, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_thread -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.container, ThreadFragment.newInstance()).addToBackStack("").commit()
                Log.d(TAG, "onOptionsItemSelected() called with: item = $item")
            }
            R.id.action_history -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.container, HistoryWeatherListFragment.newInstance())
                    .addToBackStack("").commit()
                Log.d(TAG, "onOptionsItemSelected() called with: item = $item")
            }
            R.id.action_work_with_content_provider -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.container, WorkWithContentProviderFragment.newInstance())
                    .addToBackStack("").commit()
                Log.d(TAG, "onOptionsItemSelected() called with: item = $item")
            }
            R.id.action_menu_google_maps -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.container, MapsFragment()).addToBackStack("").commit()
                Log.d(TAG, "onOptionsItemSelected() called with: item = $item")
            }
        }
        return super.onOptionsItemSelected(item)
    }
}