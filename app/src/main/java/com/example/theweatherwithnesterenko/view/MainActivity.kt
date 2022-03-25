package com.example.theweatherwithnesterenko.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.theweatherwithnesterenko.R

import com.example.theweatherwithnesterenko.view.main.MainFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance()).commit()
        }
    }
}