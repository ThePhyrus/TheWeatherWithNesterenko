package com.example.theweatherwithnesterenko.lesson4

import android.util.Log
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.theweatherwithnesterenko.R
import com.example.theweatherwithnesterenko.lesson1.Person
import com.example.theweatherwithnesterenko.lesson3.Lesson3
import com.example.theweatherwithnesterenko.view.MainActivity

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
        f1: (float: Float) -> Unit,
        f2: (double: Double) -> Short,
        f3: (char: Char) -> Boolean
    ) {
        f1(1f)
        val short = f2(1.0)
        val boolean = f3('@')
    }

    fun main(mainActivity: MainActivity) {
        Log.d("@@@", "до опытов: $person")

        val newPersonLet = person?.let {
            it.age = 10
            it.name = "LetName"
            1
        }
        Log.d("@@@", "после let: $person")

        val newPersonRun = person?.run {
            age = 99
            name = "RunName"
            2
        }
        Log.d("@@@", "после run: $person")

        val newPersonAlso = person?.also {
            it.age = 55
            it.name = "AlsoName"
            3
        }
        Log.d("@@@", "после also: $person")

        val newPersonApply = person?.apply {
            age = 66
            name = "ApplyName"
            4
        }
        Log.d("@@@", "после apply: $person")

        Log.d("@@@", "результат $newPersonLet")
        Log.d("@@@", "результат $newPersonRun")
        Log.d("@@@", "результат $newPersonAlso")
        Log.d("@@@", "результат $newPersonApply")


        with(person!!) {
            age = 66
            name = "ApplyName"
        }

        /** Способ 1 (неправильный) **/
        val textView = TextView(mainActivity)
        textView.text = "oeirjg"
        //code
        //code
        //code
        //code
        //code
        textView.textSize = 30f
        mainActivity.findViewById<ConstraintLayout>(R.id.layout).addView(textView)

        /** Способ 2 (правильный) **/

        mainActivity.findViewById<ConstraintLayout>(R.id.layout)
            .addView(TextView(mainActivity).apply {
                text = "oeirjg"
                textSize = 30f
            })

    }


    private val person: Person? = Person()
    fun was() {
        Log.d("@@@", "Не был $pr")


    }


}