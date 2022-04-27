package com.example.theweatherwithnesterenko.view

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.theweatherwithnesterenko.MyApp
import com.example.theweatherwithnesterenko.R
import com.example.theweatherwithnesterenko.lesson6.MainService
import com.example.theweatherwithnesterenko.lesson6.TheBroadcastReceiver
import com.example.theweatherwithnesterenko.lesson6.ThreadFragment
import com.example.theweatherwithnesterenko.utils.*
import com.example.theweatherwithnesterenko.view.weatherlist.WeatherListFragment


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
        registerReceiver(theReceiver, IntentFilter(KEY_WAVE_THE_ACTION)) // регистрация ресивера на голбальной волне
        registerReceiver(theReceiver, IntentFilter(ACTION_AIRPLANE_MODE)) // регистрация ресивера на голбальной волне
//        LocalBroadcastManager.getInstance(this).registerReceiver(theReceiver, IntentFilter(KEY_WAVE_THE_ACTION)) // регистрация локальная

        val sp = getSharedPreferences(KEY_SP_FILE_NAME_1, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putBoolean(KEY_SP_FILE_NAME_1_KEY_IS_RUSSIAN, true)
        editor.apply()

        val spMy = getSharedPreferences(KEY_SP_MY_FILE_1, Context.MODE_PRIVATE)
        val editorMy = spMy.edit()
        editorMy.putInt(KEY_SP_MY_FILE_1_KEY_INT, 5)
        editorMy.apply()

        val spString = getSharedPreferences(KEY_SP_MY_FILE_2,Context.MODE_PRIVATE)
        val spEditor = spString.edit()
        spEditor.putString(KEY_SP_MY_FILE_2_KEY_STRING, "string")
        spEditor.apply()


        val defaultValueIsRussian = true
        sp.getBoolean(KEY_SP_FILE_NAME_1_KEY_IS_RUSSIAN,defaultValueIsRussian)

        val spFloat = getSharedPreferences(KEY_SP_MY_FILE_3, Context.MODE_PRIVATE)
        val spEditorFloat = spFloat.edit()
        spEditorFloat.putFloat(KEY_SP_MY_FILE_4_KEY_FLOAT, 0.0f)
        spEditor.apply()


        val spFloatDefValue = 0.0f
        spFloat.getFloat(KEY_SP_MY_FILE_4_KEY_FLOAT, spFloatDefValue)


//        Thread{ //todo try 1 variant
//            MyApp.getHistoryDao().getAll()
//        }.start() //todo try 1 variant
        MyApp.getHistoryDao().getAll() // cheaters variant in MyApp.kk selected

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu1, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_thread -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ThreadFragment.newInstance()).commit()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}