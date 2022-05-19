package com.example.theweatherwithnesterenko.view

import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.theweatherwithnesterenko.MainApp
import com.example.theweatherwithnesterenko.R
import com.example.theweatherwithnesterenko.BroadcastReceiver

import com.example.theweatherwithnesterenko.WorkWithContentProviderFragment
import com.example.theweatherwithnesterenko.utils.*
import com.example.theweatherwithnesterenko.view.historylist.HistoryWeatherListFragment
import com.example.theweatherwithnesterenko.view.weatherlist.WeatherListFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

// todo HW научиться хранить в базе данных широту и долготу
//TODO выводить проверки, вызвать картинку, shared preferences (сохранить настройки приложения
//FIXME:
// - кнопки меню добавляются в backstack,
// - ui, выводимая информация,
// - возможность звонить,
// - дополнительные версии, ресурсы,
// - обработка ошибок, обработка ответов сервера,
// - порядок в snackbar,
// - выводить иконку в истории запросов!!!???,

class MainActivity : AppCompatActivity() { //todo разобрать бардак в этом классе
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, WeatherListFragment.newInstance()).commit()
        }
        catchToken()
        createReceiver()
        setupSP()

        Thread{
            MainApp.getHistoryDao().getAll()
        }.start()
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

    private fun createReceiver() {//FIXME сколько угодно можно регистрировать ресиверов одной этой функцией?
        val receiver = BroadcastReceiver() // создаётся ресивер (приёмник)
        registerReceiver(
            receiver,
            IntentFilter(KEY_WAVE_THE_ACTION)
        )
        registerReceiver(
            receiver,
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
            }
            R.id.action_work_with_content_provider -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.container, WorkWithContentProviderFragment.newInstance())
                    .addToBackStack("").commit()
            }
            R.id.action_menu_google_maps -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.container, MapsFragment()).addToBackStack("").commit()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}