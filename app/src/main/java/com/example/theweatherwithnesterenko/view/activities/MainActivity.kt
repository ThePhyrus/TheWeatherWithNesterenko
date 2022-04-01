package com.example.theweatherwithnesterenko.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.theweatherwithnesterenko.R
import com.example.theweatherwithnesterenko.view.adapters.WeatherListFragment


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainContainer, WeatherListFragment.newInstance()).commit()
        }
    }
}
