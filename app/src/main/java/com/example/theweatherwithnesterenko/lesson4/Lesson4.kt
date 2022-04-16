package com.example.theweatherwithnesterenko.lesson4

import com.example.theweatherwithnesterenko.lesson3.Lesson3

class Lesson4 {


    lateinit var lesson3: Lesson3  // 1 способ
    fun some1() { //первый способ
        lesson3.usual1("что-то произошло 1 способ")
    }

    var f = fun(string: String){}
    fun some2() { // 2 способ
        f("что-то произошло 2 способ")
    }


    lateinit var speakable: Speakable

    fun some3(){  // 3.1 способ
        speakable.f("что-то произошло 3.1 способ", 1)
    }

    fun some4(){  // 3.2 способ
        speakable.f("что-то произошло 3.2 способ", 1)
    }


    fun some5(){  // 4.1 способ
        speakable.f("что-то произошло 4.1 способ", 1)
    }
    fun some6(_speakable: Speakable){  // 4.2 способ
        _speakable.f("что-то произошло 4.2 способ", 1)
    }

}