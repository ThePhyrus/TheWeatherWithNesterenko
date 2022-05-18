package com.example.theweatherwithnesterenko

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.theweatherwithnesterenko.domain.room.HistoryDao
import com.example.theweatherwithnesterenko.domain.HistoryDataBase
import java.lang.IllegalStateException

class TheWeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }

    companion object {
        private var db: HistoryDataBase? = null
        private var appContext: TheWeatherApplication? = null
        fun getHistoryDao(): HistoryDao {
            if (db == null) {
                if (appContext != null) {
                    db = Room.databaseBuilder(appContext!!, HistoryDataBase::class.java, "test")
                        .addMigrations(migration_1_2)
                        .build()
                } else {
                    throw IllegalStateException("что-то пошло не так и у нас пустой appContext") //todo в строковые ресурсы
                }
            }
            return db!!.historyDao()
        }

        private val migration_1_2: Migration = object : Migration(1, 2) {//FIXME объясните, пожалуйста, магию
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE history_table ADD column condition TEXT NOT NULL DEFAULT ''")
            }
        }
    }
}