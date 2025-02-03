package com.example.proyectoandroidsegundotrimestre.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "videojuegos")
data class Videojuego(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "titulo") val titulo: String,
    @ColumnInfo(name = "anio_lanzamiento") val anioLanzamiento: Int,
    @ColumnInfo(name = "imagen_portada") val imagenPortada: String
)
