package com.example.theweatherwithnesterenko.domain.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(HistoryEntity::class), version = 2)
abstract class MyDB:RoomDatabase() {
    abstract fun historyDao(): HistoryDao
//    abstract fun historyDao1():HistoryDao
}