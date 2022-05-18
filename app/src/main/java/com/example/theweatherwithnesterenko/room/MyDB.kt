package com.example.theweatherwithnesterenko.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.theweatherwithnesterenko.domain.room.HistoryDao
import com.example.theweatherwithnesterenko.domain.room.HistoryEntity

@Database(entities = arrayOf(HistoryEntity::class), version = 2)
abstract class MyDB:RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}