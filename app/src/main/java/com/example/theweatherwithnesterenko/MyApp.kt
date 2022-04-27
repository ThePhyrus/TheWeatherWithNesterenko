package com.example.theweatherwithnesterenko

import android.app.Application
import androidx.room.Room
import com.example.theweatherwithnesterenko.domain.room.HistoryDao
import com.example.theweatherwithnesterenko.domain.room.MyDB
import java.lang.IllegalStateException

class MyApp:Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }
    companion object{
        private var db: MyDB? = null
        private var appContext: MyApp? = null
        fun getHistoryDao(): HistoryDao{
            if (db == null){
                if (appContext != null){
//                    Thread{ //todo try 2 variant
                        db = Room.databaseBuilder(appContext!!,MyDB::class.java,"test")
                            .allowMainThreadQueries() // todo FOR CHEATERS ONLY
                            .build()
//                    }.start() //todo try 2 variant
                }else {
                    throw IllegalStateException("что-то пошло не так и у нас пустой appContext")
                }
            }
            return db!!.historyDao()
        }
    }
}