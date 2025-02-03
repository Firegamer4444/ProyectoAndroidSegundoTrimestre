package com.example.proyectoandroidsegundotrimestre.models

import com.example.proyectoandroidsegundotrimestre.room.Videojuego

data class ReseniaModel(
    var idResenia: Int,
    var videojuego: Videojuego,
    var comentario: String,
    var puntuacion: Float
)
