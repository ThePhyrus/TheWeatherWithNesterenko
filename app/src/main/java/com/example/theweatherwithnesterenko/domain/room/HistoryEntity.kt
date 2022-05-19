package com.example.theweatherwithnesterenko.domain.room

import androidx.room.Entity
import androidx.room.PrimaryKey


const val ID = "id"
const val NAME = "city"
const val TEMPERATURE = "temperature"
const val FEELS_LIKE = "feelsLike"
const val ICON = "icon"
const val CONDITION = "condition"


@Entity(tableName = "history_table")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val city: String,
    /*val timeStamp:Long, todo HW первичный ключ связкой city+timeStamp ???*/
    val temperature: Int,
    val feelsLike: Int = 10,
    val icon: String = "skn_n",
    val condition: String = "default_condition" //FIXME обязательно ли значение по умолчанию? Зачем оно сдесь?
)

