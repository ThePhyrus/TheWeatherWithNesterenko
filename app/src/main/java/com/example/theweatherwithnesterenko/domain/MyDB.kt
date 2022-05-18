package com.example.theweatherwithnesterenko.domain

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.theweatherwithnesterenko.domain.HistoryDao
import com.example.theweatherwithnesterenko.domain.room.HistoryEntity

@Database(entities = arrayOf(HistoryEntity::class), version = 2)
abstract class MyDB:RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}