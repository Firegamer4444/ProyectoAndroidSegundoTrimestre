package com.example.proyectoandroidsegundotrimestre.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class Usuario (
    @PrimaryKey()
    val usuario: String,
    val contraseña: String
)




