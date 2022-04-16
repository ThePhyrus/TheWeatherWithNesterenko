package com.example.theweatherwithnesterenko.view.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.theweatherwithnesterenko.R
import com.example.theweatherwithnesterenko.lesson3.Lesson3
import com.example.theweatherwithnesterenko.lesson4.Lesson4
import com.example.theweatherwithnesterenko.view.adapters.WeatherListFragment

const val TAG: String = "@@@"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainContainer, WeatherListFragment.newInstance()).commit()
        }


        val lesson3 = Lesson3()
        val lesson4 = Lesson4()
        lesson4.lesson3 = lesson3
        lesson4.some1()// 1 способ

        lesson4.f = lesson3.f  // 2 способ
        lesson4.some2()

        lesson4.speakable = lesson3  // 3.1 способ
        lesson4.some3()

        lesson4.speakable = lesson3.callback  // 3.2 способ
        lesson4.some4()

        lesson4.speakable = lesson3.callbackLambda1  // 4.1 способ
        lesson4.some5()

        lesson4.some6 { string: String, i: Int ->
            Log.d(TAG, "Сообщение: $string") // 4.2 способ
            1.0
        }

        lesson4.was()


    }

    fun Lesson4.was(){
        Log.d(TAG, "Был $pr")
    }

}
