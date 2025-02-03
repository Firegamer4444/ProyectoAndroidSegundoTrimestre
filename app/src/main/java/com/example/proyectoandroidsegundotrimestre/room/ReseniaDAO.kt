package com.example.proyectoandroidsegundotrimestre.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReseniaDAO {
    @Insert
    suspend fun insertar(resenia: Resenia): Long

    @Query("SELECT * FROM resenas WHERE usuario_id = :usuarioId")
    suspend fun obtenerResenasPorUsuario(usuarioId: String): List<Resenia>

    @Query("SELECT COUNT(*) FROM resenas WHERE usuario_Id = :usuarioId AND juego_id = :juegoId")
    suspend fun existeResenia(usuarioId: String, juegoId: Int): Int

    @Query("DELETE FROM resenas WHERE id = :reseniaId")
    suspend fun eliminarResenia(reseniaId: Int)

    @Query("UPDATE resenas SET juego_id = :idViedojuego , texto_resena = :comentario, puntuacion = :puntuacion WHERE id = :id")
    suspend fun actualizarResenia(id: Int,idViedojuego: Int , comentario: String, puntuacion: Float)

}