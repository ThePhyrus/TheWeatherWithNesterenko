package com.example.theweatherwithnesterenko.domain.room

import android.database.Cursor
import androidx.room.*


@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: HistoryEntity)

    @Delete()
    fun delete(entity: HistoryEntity)

    @Update
    fun update(entity: HistoryEntity)

    @Query("SELECT * FROM history_table")
    fun getAll(): List<HistoryEntity>

    @Query("SELECT * FROM history_table WHERE id = :id")
    fun getHistoryCursor(id: Long): Cursor

    @Query("SELECT * FROM history_table")
    fun getHistoryCursor(): Cursor

    @Query("SELECT * FROM history_table WHERE city = :city")
    fun getHistoryCity(city: String): List<HistoryEntity>

    @Query("DELETE FROM history_table WHERE id = :id")
    fun deleteByID(id: Long)

}