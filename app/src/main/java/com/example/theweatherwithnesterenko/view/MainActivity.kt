package com.example.theweatherwithnesterenko.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.theweatherwithnesterenko.R
import com.example.theweatherwithnesterenko.lesson6.ThreadFragment
import com.example.theweatherwithnesterenko.view.weatherlist.WeatherListFragment


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, WeatherListFragment.newInstance()).commit()
        }
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