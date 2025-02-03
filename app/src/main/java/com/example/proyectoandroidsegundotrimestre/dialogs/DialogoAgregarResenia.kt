package com.example.proyectoandroidsegundotrimestre.dialogs


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.example.proyectoandroidsegundotrimestre.adapters.ReseñasAdapter
import com.example.proyectoandroidsegundotrimestre.databinding.ReseniaDialogBinding
import com.example.proyectoandroidsegundotrimestre.models.ReseniaModel
import com.example.proyectoandroidsegundotrimestre.room.DatabaseProvider
import com.example.proyectoandroidsegundotrimestre.room.Resenia
import com.example.proyectoandroidsegundotrimestre.room.Videojuego
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DialogoAgregarResenia : DialogFragment() {

    private var _binding: ReseniaDialogBinding? = null
    private val binding get() = _binding!!

    private lateinit var listaVideojuegos: List<Videojuego>
    private var videojuegoSeleccionado: Videojuego? = null

    var modificar = false
    private var posicionResenia = 0
    lateinit var resenia: ReseniaModel

    lateinit var adaptador: ReseñasAdapter
    private var usuario = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ReseniaDialogBinding.inflate(inflater, container, false)

        val db = DatabaseProvider.getDatabase(requireContext())
        val videojuegoDAO = db.videojuegoDao()
        val reseniaDAO = db.reseniaDAO()

        // Obtener los videojuegos desde la base de datos y rellenar el Spinner
        lifecycleScope.launch {
            listaVideojuegos = videojuegoDAO.obtenerJuegos()!!

            val nombresVideojuegos = listaVideojuegos.map { it.titulo } // Extraemos los nombres de los videojuegos

            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, nombresVideojuegos)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerVideojuegos.adapter = adapter

            // Establecer el listener para el Spinner
            binding.spinnerVideojuegos.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parentView: AdapterView<*>, view: View?, position: Int, id: Long) {
                    videojuegoSeleccionado = listaVideojuegos[position]
                }

                override fun onNothingSelected(parentView: AdapterView<*>) {
                    videojuegoSeleccionado = null
                }
            }

            if (modificar){
                initCampos()
            }
        }



        binding.confirmarButton.setOnClickListener {
            if (validarCampos()) {
                val comentario = binding.comentarioEditText.text.toString()
                val puntuacion = binding.puntuacionRatingBar.rating
                val videojuegoId = videojuegoSeleccionado?.id ?: 0

                lifecycleScope.launch {
                    val existe = reseniaDAO.existeResenia(usuario, videojuegoId) > 0

                    if (existe) {
                        showToast("Ya has escrito una reseña para este juego")
                    } else {
                        // si se va a modificar se hace un update y si no se hace un insert
                        if (modificar){
                            reseniaDAO.actualizarResenia(resenia.idResenia, videojuegoId, comentario, puntuacion)

                            dismiss() // Cierra el diálogo después de modificar la reseña
                            withContext(Dispatchers.Main) {
                                adaptador.updateItem(posicionResenia, ReseniaModel(resenia.idResenia, videojuegoSeleccionado!!, comentario, puntuacion))
                            }
                        }
                        else{
                            val idResenia = reseniaDAO.insertar(
                                Resenia(usuarioId = usuario, juegoId = videojuegoId, textoResena = comentario, puntuacion = puntuacion)
                            ).toInt()

                            dismiss() // Cierra el diálogo después de agregar la reseña
                            withContext(Dispatchers.Main) {
                                adaptador.addItem(ReseniaModel(idResenia, videojuegoSeleccionado!!, comentario, puntuacion))
                            }
                        }

                    }
                }
            }
        }


        return binding.root
    }

    // Función para validar los campos
    private fun validarCampos(): Boolean {
        val comentario = binding.comentarioEditText.text.toString()
        val puntuacion = binding.puntuacionRatingBar.rating

        if (comentario.isBlank()) {
            binding.comentarioEditText.error = "El comentario no puede estar vacío"
            return false
        }

        if (puntuacion == 0f) {
            showToast("Debes asignar una puntuacion")
            return false
        }

        if (videojuegoSeleccionado == null) {
            showToast("Debes seleccionar un videojuego")
            return false
        }

        return true
    }

    // Mostrar un mensaje de toast
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun initCampos(){
        binding.comentarioEditText.setText(resenia.comentario)
        binding.puntuacionRatingBar.rating = resenia.puntuacion
        binding.spinnerVideojuegos.setSelection(listaVideojuegos.indexOf(listaVideojuegos.find { it.id == resenia.videojuego.id }))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Limpiar el binding
    }

    fun setUsuario(usuario: String){
        this.usuario = usuario
    }

    fun setPosicion(posicion: Int){
        this.posicionResenia = posicion
    }

}
