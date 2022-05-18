package com.example.theweatherwithnesterenko

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.theweatherwithnesterenko.domain.HistoryDao
import com.example.theweatherwithnesterenko.domain.MyDB
import java.lang.IllegalStateException

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }

    companion object {
        private var db: MyDB? = null
        private var appContext: MyApp? = null
        fun getHistoryDao(): HistoryDao {
            if (db == null) {
                if (appContext != null) {
                    db = Room.databaseBuilder(appContext!!, MyDB::class.java, "test")
                        .addMigrations(migration_1_2)
                        .build()
                } else {
                    throw IllegalStateException("что-то пошло не так и у нас пустой appContext")
                }
            }
            return db!!.historyDao()
        }

        private val migration_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE history_table ADD column condition TEXT NOT NULL DEFAULT ''")
            }
        }
    }
}