package com.example.theweatherwithnesterenko.lesson4

import android.util.Log
import com.example.theweatherwithnesterenko.lesson3.Lesson3
import com.example.theweatherwithnesterenko.view.activities.TAG

class Lesson4 {

   val pr = 777


    lateinit var lesson3: Lesson3  // 1 способ
    fun some1() { //первый способ
        lesson3.usual1("что-то произошло 1 способ")
    }

    var f = fun(string: String) {}
    fun some2() { // 2 способ
        f("что-то произошло 2 способ")
    }


    lateinit var speakable: Speakable

    fun some3() {  // 3.1 способ
        speakable.f("что-то произошло 3.1 способ", 1)
    }

    fun some4() {  // 3.2 способ
        speakable.f("что-то произошло 3.2 способ", 1)
    }


    fun some5() {  // 4.1 способ
        speakable.f("что-то произошло 4.1 способ", 1)
    }

    fun some6(_speakable: Speakable) {  // 4.2 способ
        _speakable.f("что-то произошло 4.2 способ", 1)
    }


    val _f = fun(float: Float): Unit {

    }

    val __f = fun(double: Double): Short {
        return 1
    }

    val _c = fun(char: Char): Boolean {
        return true
    }

    private fun funHigh(
        _f: (float: Float) -> Unit,
        __f: (double: Double) -> Short,
        _c: (char: Char) -> Boolean
    ) {
        _f(1f)
        val short = __f(1.0)
        val boolean = _c('@')
    }

    fun main(){
        funHigh(_f, __f, _c)
    }



    fun was(){
        Log.d(TAG, "Не был $pr")
    }


}