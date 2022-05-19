package com.example.theweatherwithnesterenko

import android.app.Application
import android.util.Log
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.theweatherwithnesterenko.domain.room.HistoryDao
import com.example.theweatherwithnesterenko.domain.HistoryDataBase
import com.example.theweatherwithnesterenko.utils.TAG
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import java.lang.IllegalStateException

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this
        catchToken()
    }

    companion object {
        private var db: HistoryDataBase? = null
        private var appContext: MainApp? = null
        fun getHistoryDao(): HistoryDao {
            if (db == null) {
                if (appContext != null) {
                    db = Room.databaseBuilder(appContext!!, HistoryDataBase::class.java, "test")
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
    private fun catchToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            val token = task.result
            Log.d(TAG, "catchToken: $token")
        })
    }
}