package com.example.theweatherwithnesterenko.view

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.theweatherwithnesterenko.R
import com.example.theweatherwithnesterenko.lesson6.MainService
import com.example.theweatherwithnesterenko.lesson6.TheBroadcastReceiver
import com.example.theweatherwithnesterenko.lesson6.ThreadFragment
import com.example.theweatherwithnesterenko.utils.KEY_ACTIVITY_MESSAGE
import com.example.theweatherwithnesterenko.view.weatherlist.WeatherListFragment


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, WeatherListFragment.newInstance()).commit()
        }

        startService(Intent(this, MainService::class.java).apply {
   //         putExtra(KEY_1, "${R.string.hallo_from_activity}") // передаём строку в сервис //FIXME передаётся int
            putExtra(KEY_ACTIVITY_MESSAGE, "Привет, Сервис! Я Астивити.")
        })

        val theReceiver = TheBroadcastReceiver() // создаётся ресивер (приёмник)
        registerReceiver(theReceiver, IntentFilter("theaction")) // регистрация ресивера на голбальной волне
//        LocalBroadcastManager.getInstance(this).registerReceiver(theReceiver, IntentFilter("theaction")) // регистрация локально
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu1, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_thread -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ThreadFragment.newInstance()).commit()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}