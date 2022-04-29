package com.example.theweatherwithnesterenko.domain.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.theweatherwithnesterenko.R


@Entity(tableName = "history_table")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Long,
    val city: String,
    /*val timeStamp:Long, todo HW первичный ключ связкой city+timeStamp ???*/
    val temperature: Int,
    val feelsLike: Int,
    val icon: String,
    val condition:String = "default_condition" //FIXME обязательно ли значение по умолчанию? Зачем оно сдесь?
)

