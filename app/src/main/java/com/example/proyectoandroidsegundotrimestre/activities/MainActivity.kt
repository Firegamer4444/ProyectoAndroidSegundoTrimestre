package com.example.proyectoandroidsegundotrimestre.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectoandroidsegundotrimestre.R
import com.example.proyectoandroidsegundotrimestre.adapters.ReseñasAdapter
import com.example.proyectoandroidsegundotrimestre.databinding.ActivityMainBinding
import com.example.proyectoandroidsegundotrimestre.dialogs.DialogoAgregarResenia
import com.example.proyectoandroidsegundotrimestre.models.ReseniaModel
import com.example.proyectoandroidsegundotrimestre.room.AppDatabase
import com.example.proyectoandroidsegundotrimestre.room.DatabaseProvider
import com.example.proyectoandroidsegundotrimestre.room.ReseniaDAO
import com.example.proyectoandroidsegundotrimestre.room.Videojuego
import com.example.proyectoandroidsegundotrimestre.room.VideojuegoDAO
import kotlinx.coroutines.launch

class MainActivity : BaseActivity(){

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ReseñasAdapter

    private lateinit var bundle: Bundle
    private lateinit var usuario: String

    private lateinit var db: AppDatabase
    private lateinit var reseniaDAO: ReseniaDAO
    private lateinit var videojuegoDAO: VideojuegoDAO

    var listaResenias = mutableListOf<ReseniaModel>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        bundle = intent.extras!!
        usuario = bundle.getString("usuario")!!


        setUpNavigatorDrawer(binding.drawerLayout , binding.navigationView)

        setSupportActionBar(binding.toolbarLayout.toolbar)
        supportActionBar?.title = "Reseñas"

        db = DatabaseProvider.getDatabase(this)
        reseniaDAO = db.reseniaDAO()
        videojuegoDAO = db.videojuegoDao()



        lifecycleScope.launch {

            // inserta los videojuegos si es la primera vez que se inicia el aplicativo
            if (videojuegoDAO.contarVideojuegos() == 0) {
                videojuegoDAO.insertar(Videojuego(1, "Zelda: Tears of the kingdom", 2023, "zelda_cover"))
                videojuegoDAO.insertar(Videojuego(2, "Super mario odyssey", 2017, "super_mario_odyssey_cover"))
                videojuegoDAO.insertar(Videojuego(3, "Xenoblade Chronicles", 2010, "xenoblade_cover"))
            }


            // se cargan las reseñas de los usuarios
            val resenias = reseniaDAO.obtenerResenasPorUsuario(usuario)

            listaResenias = mutableListOf()

            for (resenia in resenias) {

                val videojuego = videojuegoDAO.obtenerJuegoPorId(resenia.juegoId)!!

                listaResenias.add(ReseniaModel(resenia.id ,videojuego, resenia.textoResena, resenia.puntuacion))
            }
            initRecyclerView()
        }


        // se le añade el listener al boton flotante para añadir una reseña
        binding.floatingActionButton.setOnClickListener{view ->

            val dialogoNuevaResenia = DialogoAgregarResenia()
            dialogoNuevaResenia.setUsuario(usuario)
            dialogoNuevaResenia.adaptador = adapter
            dialogoNuevaResenia.modificar = false
            dialogoNuevaResenia.show(supportFragmentManager, "DialogoNuevaResenia")
        }

    }

    // Inicializa el RecyclerView
    private fun initRecyclerView() {
        // Asignamos tipo de layout y adaptador al RecyclerView
        binding.videojuegosRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ReseñasAdapter(this , listaResenias, lifecycleScope)
        binding.videojuegosRecyclerView.adapter = adapter

    }



}