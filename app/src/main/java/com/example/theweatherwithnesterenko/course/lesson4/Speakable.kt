package com.example.theweatherwithnesterenko.course.lessons.lesson4

fun interface Speakable {
    /*
    Этот интерфейс отвечает за "общение" классов. Тот, класс, который реализует этот интерфейс,
    сможет принимать в себя сообщения. А тот класс, которому будет переданна реализация, сможет
    эту функцию вызывать.
     */

    fun f (string:String, i:Int):Double
}