package com.example.practicaguias_pedro.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ListaDao {
    @Query("SELECT * FROM Lista_Actividades")
    fun getAllElements(): MutableList<Lista>

    @Insert
    fun addElemento(taskEntity: Lista):Long

    @Query("SELECT * FROM Lista_Actividades WHERE id like :id")
    fun getElementoId(id: Long):Lista

    @Update
    fun updateLista(taskEntity: Lista):Int

    @Delete
    fun deleteLista(taskEntity: Lista):Int
}