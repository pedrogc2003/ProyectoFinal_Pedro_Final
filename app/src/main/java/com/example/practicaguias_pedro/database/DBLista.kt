package com.example.practicaguias_pedro.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Lista::class), version = 1)
abstract class DBLista: RoomDatabase() {
    abstract fun listaDao(): ListaDao
}