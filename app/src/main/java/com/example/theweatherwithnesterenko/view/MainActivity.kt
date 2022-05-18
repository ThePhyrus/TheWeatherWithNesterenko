package com.example.theweatherwithnesterenko.view

import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.theweatherwithnesterenko.MyApp
import com.example.theweatherwithnesterenko.R

import com.example.theweatherwithnesterenko.TheBroadcastReceiver
import com.example.theweatherwithnesterenko.WorkWithContentProviderFragment
import com.example.theweatherwithnesterenko.utils.*
import com.example.theweatherwithnesterenko.view.historylist.HistoryWeatherListFragment
import com.example.theweatherwithnesterenko.view.weatherlist.WeatherListFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

// todo HW научиться хранить в базе данных широту и долготу
//TODO выводить проверки, вызвать картинку, shared preferences (сохранить настройки приложения
//FIXME:
// - кнопки fab, ui, выводимая информация, возможность звонить, дополнительные версии, ресурсы,
// обработка ошибок, обработка ответов сервера, snackbar, class MainViewModel,
// class DetailsViewModel, android:id="@+id/ivIcon",

class MainActivity : AppCompatActivity() { //todo разобрать бардак в этом классе
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, WeatherListFragment.newInstance()).commit()
        }

        createReceiver()

        setupSP()

        Thread{
            MyApp.getHistoryDao().getAll()
        }.start()

        catchToken()

    }

    private fun setupSP() {//FIXME
        val sp = getSharedPreferences(KEY_SP_FILE_NAME_1, MODE_PRIVATE)
        val editor = sp.edit()
        editor.putBoolean(KEY_SP_FILE_NAME_1_KEY_IS_RUSSIAN, true)
        editor.apply()
        val defaultValueIsRussian = true
        sp.getBoolean(KEY_SP_FILE_NAME_1_KEY_IS_RUSSIAN, defaultValueIsRussian)
    }

    private fun catchToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            val token = task.result
            Log.d(TAG, "$token")
        })
    }

    private fun createReceiver() {
        val theReceiver = TheBroadcastReceiver() // создаётся ресивер (приёмник)
        registerReceiver(
            theReceiver,
            IntentFilter(KEY_WAVE_THE_ACTION)
        )
        registerReceiver(
            theReceiver,
            IntentFilter(ACTION_AIRPLANE_MODE)
        )
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

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