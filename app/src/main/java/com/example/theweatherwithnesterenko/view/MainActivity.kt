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


//FIXME: это нужно как-то починить
// - подправить ui и выводимую информацию,
// - сделать дополнительные версии,
// - ресурсы (как там порядок навести),
// - обработка ошибок, обработка ответов сервера,
// - порядок в snackbar,
// - выводить иконку в истории запросов!!!???,
// - научиться сохранять настройки приложения,

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, WeatherListFragment.newInstance()).commit()
        }
        createReceiver()
        setupSP()

        Thread {
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {//FIXME не переключаются между собой
        when (item.itemId) {
            R.id.action_history -> {
                val fragmentA = supportFragmentManager.findFragmentByTag("tag")
                if (fragmentA == null) {
                    supportFragmentManager.apply {
                        beginTransaction()
                            .replace(
                                R.id.container,
                                HistoryWeatherListFragment.newInstance(),
                                "tag"
                            )
                            .addToBackStack("")
                            .commit()
                    }
                }
            }
            R.id.action_work_with_content_provider -> {
                val fragmentB = supportFragmentManager.findFragmentByTag("tag")
                if (fragmentB == null) {
                    supportFragmentManager.apply {
                        beginTransaction()
                            .replace(
                                R.id.container,
                                WorkWithContentProviderFragment.newInstance(),
                                "tag"
                            )
                            .addToBackStack("")
                            .commit()
                    }
                }
            }
            R.id.action_menu_google_maps -> {
                val fragmentB = supportFragmentManager.findFragmentByTag("tag")
                if (fragmentB == null) {
                    supportFragmentManager.apply {
                        beginTransaction()
                            .replace(
                                R.id.container,
                                MapsFragment(),
                                "tag"
                            )
                            .addToBackStack("")
                            .commit()
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}