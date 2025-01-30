package com.example.proyectoandroidsegundotrimestre.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoandroidsegundotrimestre.R
import com.example.proyectoandroidsegundotrimestre.models.Reseña

class ReseñasAdapter (private val context: Context, private val listaReseñas:List<Reseña>, private val onItemClick: (Int) -> Unit): RecyclerView.Adapter<ReseñasAdapter.AdapterViewHolder>() {

    // Definimos el ViewHolder interno al adaptador. Se utiliza para el renderizado e instanciación
    // de cada elemento del RecyclerView
    class AdapterViewHolder(item: View): RecyclerView.ViewHolder(item) {
        val card: CardView = item.findViewById(R.id.reseñaCardView)
        val tituloText: TextView = item.findViewById(R.id.tituloTextView)
        val sinopsisText: TextView = item.findViewById(R.id.comentarioTextView)
        val portadaImage: ImageView = item.findViewById(R.id.videojuegoImageView)
    }

    // Infla la vista de cada elemento del listado (CardView) en el RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return AdapterViewHolder(layoutInflater.inflate(R.layout.item_rcv_resenia, parent, false))
    }

    // Número de elementos de la lista
    override fun getItemCount(): Int = listaReseñas.size

    // Llamada a ViewHolder para el renderizado y asignación de los datos a cada elemento del listado
    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        holder.tituloText.text = listaReseñas[position].videojuego
        holder.sinopsisText.text = listaReseñas[position].comentario
        holder.portadaImage.setImageResource(context.resources.getIdentifier(listaReseñas[position].imagenVideojuego, "drawable", context.packageName))
        // Asignación evento OnClick al control CardView
        holder.card.setOnClickListener{ onItemClick(position) }
    }
}