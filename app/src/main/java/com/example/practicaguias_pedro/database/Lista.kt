package com.example.practicaguias_pedro.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Lista_Actividades")
data class Lista (
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0,
    var nombre:String = "",
    var activo:Boolean = false
)