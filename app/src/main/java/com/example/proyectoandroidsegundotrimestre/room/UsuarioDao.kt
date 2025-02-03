package com.example.proyectoandroidsegundotrimestre.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UsuarioDao {
    @Insert
    suspend fun insert(usuario: Usuario)

    @Query("SELECT * FROM usuarios WHERE usuario = :usuario AND contraseña = :contraseña LIMIT 1")
    suspend fun login(usuario: String, contraseña: String): Usuario?
}