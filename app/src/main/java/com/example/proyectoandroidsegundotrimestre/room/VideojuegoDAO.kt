package com.example.proyectoandroidsegundotrimestre.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface VideojuegoDAO {
    @Insert
    suspend fun insertar(videojuego: Videojuego)

    @Query("SELECT * FROM videojuegos WHERE id = :idJuego")
    suspend fun obtenerJuegoPorId(idJuego: Int): Videojuego?

    @Query("SELECT * FROM videojuegos")
    suspend fun obtenerJuegos(): List<Videojuego>?

    @Query("SELECT COUNT(*) FROM videojuegos")
    suspend fun contarVideojuegos(): Int

}