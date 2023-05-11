package com.example.practicaguias_pedro.database

import android.app.Application
import androidx.room.Room
import com.example.practicaguias_pedro.Activitys.Otros

class MiLista: Application() {

    companion object{
        lateinit var database: DBLista
    }

    override fun onCreate() {
        super.onCreate()
        MiLista.database = Room.databaseBuilder(this, DBLista::class.java, "DBLista").build()
    }

}