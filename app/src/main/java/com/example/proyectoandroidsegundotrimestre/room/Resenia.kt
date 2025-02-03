package com.example.proyectoandroidsegundotrimestre.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "resenas",
    foreignKeys = [
        ForeignKey(
            entity = Usuario::class,
            parentColumns = arrayOf("usuario"),
            childColumns = arrayOf("usuario_id"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Videojuego::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("juego_id"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Resenia(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "usuario_id") val usuarioId: String,
    @ColumnInfo(name = "juego_id") val juegoId: Int,
    @ColumnInfo(name = "texto_resena") val textoResena: String,
    @ColumnInfo(name = "puntuacion") val puntuacion: Float
)
