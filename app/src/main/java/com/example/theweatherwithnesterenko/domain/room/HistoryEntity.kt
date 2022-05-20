package com.example.theweatherwithnesterenko.domain.room

import androidx.room.Entity
import androidx.room.PrimaryKey


const val ID = "id"
const val NAME = "city"
const val TEMPERATURE = "temperature"
const val FEELS_LIKE = "feelsLike"
const val ICON = "icon"


@Entity(tableName = "history_table")
data class HistoryEntity( // тут будут все поля из Weather плюс поле id
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val city: String,
    val temperature: Int,//FIXME почему тут нет дефолтного значения ?
    val feelsLike: Int = 10,//FIXME и зачем дефолтное значение тут?
    val icon: String = "skn_n"//FIXME а тут?
//      val timestamp: Long, //todo HW Сделать связку city и timestamp для первичного ключа
)

