package com.example.theweatherwithnesterenko.domain

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.theweatherwithnesterenko.domain.room.HistoryDao
import com.example.theweatherwithnesterenko.domain.room.HistoryEntity

//FIXME: если version = 1, то ошибка:
// Each bind variable in the query must have a matching method parameter. Cannot find method
// parameters for :city, :temperature, :feelsLike, :icon.
// - com.example.theweatherwithnesterenko.domain.room.HistoryDao.nativeInsert(java.lang.String, int, int, java.lang.String)

@Database(entities = arrayOf(HistoryEntity::class), version = 2)
abstract class HistoryDataBase:RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}