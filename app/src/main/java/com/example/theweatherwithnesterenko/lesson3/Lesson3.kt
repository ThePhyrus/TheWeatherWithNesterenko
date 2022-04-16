package com.example.theweatherwithnesterenko.lesson3

import android.util.Log
import com.example.theweatherwithnesterenko.lesson4.Speakable
import com.example.theweatherwithnesterenko.view.activities.TAG


class Lesson3 : Speakable {


    fun usual1(string: String) {  // 1 способ
        // что-то выполняется
        Log.d(TAG, "Сообщение: $string")
    }

    val f = fun(string: String) {  // 2 способ
        // что-то выполняется
        Log.d(TAG, "Сообщение: $string")
    }

    override fun f(string: String, i: Int): Double { // 3.1 способ
        // что-то выполняется
        Log.d(TAG, "Сообщение: $string")
        return 1.0
    }


    val callback = object : Speakable { // 3.2 способ
        override fun f(string: String, i: Int):Double {
            Log.d(TAG, "Сообщение: $string")
            return 1.0
        }
    }


    val callbackLambda1 = Speakable hack@{ string, i ->  // 4.1 способ
        Log.d(TAG, "Сообщение: $string")
        1.0
    }

    val callbackLambda2 = abracadabra@{ string: String, i: Int ->  // 4.2 способ
        Log.d(TAG, "Сообщение: $string")
        Log.d(TAG, "Сообщение: $string")
        Log.d(TAG, "Сообщение: $string")
        Log.d(TAG, "Сообщение: $string")
        1
    }

}