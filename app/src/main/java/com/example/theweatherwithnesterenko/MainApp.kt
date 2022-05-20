package com.example.theweatherwithnesterenko

import android.app.Application
import android.util.Log
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.theweatherwithnesterenko.domain.room.HistoryDao
import com.example.theweatherwithnesterenko.domain.MainDataBase
import com.example.theweatherwithnesterenko.utils.TAG
import com.example.theweatherwithnesterenko.utils.catchToken
import com.example.theweatherwithnesterenko.utils.mainFunction
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import java.lang.IllegalStateException

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this
        catchToken()
        mainFunction()
    }

    companion object {
        private var db: MainDataBase? = null //при первом запуске приложения база должна быть пустая
        private var appContext: MainApp? = null
        fun getHistoryDao(): HistoryDao {
            if (db == null) {
                if (appContext != null) {
                    db = Room.databaseBuilder(appContext!!, MainDataBase::class.java, "test")
                        .addMigrations(migration_1_2)
                        .build()
                } else {
                    throw IllegalStateException("${R.string.something_went_wrong}")
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