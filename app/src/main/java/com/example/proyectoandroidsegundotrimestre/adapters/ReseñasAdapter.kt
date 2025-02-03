package com.example.proyectoandroidsegundotrimestre.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoandroidsegundotrimestre.R
import com.example.proyectoandroidsegundotrimestre.databinding.ItemRcvReseniaBinding
import com.example.proyectoandroidsegundotrimestre.dialogs.DialogoAgregarResenia
import com.example.proyectoandroidsegundotrimestre.models.ReseniaModel
import com.example.proyectoandroidsegundotrimestre.room.DatabaseProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ReseñasAdapter(
    private val context: Context,
    private val listaReseniaModels: MutableList<ReseniaModel>,
    private val coroutineScope: CoroutineScope  // CoroutineScope desde MainActivity
) : RecyclerView.Adapter<ReseñasAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemRcvReseniaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int = listaReseniaModels.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRcvReseniaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val resenia = listaReseniaModels[position]

        holder.binding.tituloTextView.text = resenia.videojuego.titulo
        holder.binding.comentarioTextView.text = resenia.comentario
        holder.binding.ratingBar.rating = resenia.puntuacion
        holder.binding.videojuegoImageView.setImageResource(
            context.resources.getIdentifier(resenia.videojuego.imagenPortada, "drawable", context.packageName)
        )

        // listener para eliminar la reseña
        holder.binding.eliminarButton.setOnClickListener {
            // Diálogo de confirmación antes de borrar
            val builder = AlertDialog.Builder(context)
            builder.setMessage("¿Estás seguro de que quieres eliminar esta reseña?")
                .setTitle("Eliminar reseña")
                .setIcon(R.mipmap.ic_launcher_round)
                .setPositiveButton("Sí, eliminar") { _, _ ->
                    eliminarResenia(position)
                }
                .setNegativeButton("Cancelar") { dialog, _ ->
                    dialog.dismiss()
                }
            builder.create().show()
        }

        // listener para modificar la reseña
        holder.binding.modificarButton.setOnClickListener {
            val dialogoNuevaResenia = DialogoAgregarResenia()
            dialogoNuevaResenia.resenia = listaReseniaModels[position]
            dialogoNuevaResenia.setPosicion(position)
            dialogoNuevaResenia.adaptador = this
            dialogoNuevaResenia.modificar = true
            dialogoNuevaResenia.show((context as AppCompatActivity).supportFragmentManager, "DialogoNuevaResenia")
        }

    }

    // metodo para eliminar una reseña de la base de datos y actualizar la lista
    private fun eliminarResenia(index: Int) {
        val db = DatabaseProvider.getDatabase(context)
        val reseniaDAO = db.reseniaDAO()
        val resenia = listaReseniaModels[index]

        coroutineScope.launch {
            try {
                reseniaDAO.eliminarResenia(resenia.idResenia)  // Elimina de la base de datos
                deleteItem(index)  // Elimina del adaptador
                Toast.makeText(context, "Reseña eliminada correctamente", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(context, "Error al eliminar la reseña", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // metodo para añadir una reseña al RecyclerView
    fun addItem(item: ReseniaModel) {
        listaReseniaModels.add(item)
        notifyDataSetChanged()
    }

    // metodo para eliminar una reseña del RecyclerView
    fun deleteItem(index: Int) {
        listaReseniaModels.removeAt(index)
        notifyDataSetChanged()
    }

    // metodo para actualizar un ítem en la lista
    fun updateItem(position: Int, updatedItem: ReseniaModel) {
        listaReseniaModels[position] = updatedItem
        notifyDataSetChanged()
    }
}
