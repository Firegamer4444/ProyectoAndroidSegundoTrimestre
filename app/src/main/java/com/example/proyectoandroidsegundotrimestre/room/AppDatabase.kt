package com.example.proyectoandroidsegundotrimestre.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Usuario::class, Videojuego::class, Resenia::class], version = 4, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UsuarioDao
    abstract fun videojuegoDao(): VideojuegoDAO
    abstract fun reseniaDAO(): ReseniaDAO
}
