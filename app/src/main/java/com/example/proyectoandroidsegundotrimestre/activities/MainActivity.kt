package com.example.proyectoandroidsegundotrimestre.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectoandroidsegundotrimestre.R
import com.example.proyectoandroidsegundotrimestre.adapters.ReseñasAdapter
import com.example.proyectoandroidsegundotrimestre.databinding.ActivityMainBinding
import com.example.proyectoandroidsegundotrimestre.databinding.ToolbarBinding
import com.example.proyectoandroidsegundotrimestre.models.Reseña

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbarBinding: ToolbarBinding
    private lateinit var adapter: ReseñasAdapter

    var listaReseña = mutableListOf<Reseña>()

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

        initRecyclerView()

        setSupportActionBar(toolbarBinding.toolbar)
        supportActionBar?.title = "Reservas"

    }

    // Inicializa el RecyclerView
    private fun initRecyclerView() {
        // Asignamos tipo de layout y adaptador al RecyclerView
        binding.videojuegosRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ReseñasAdapter(this , listaReseña) {}
        binding.videojuegosRecyclerView.adapter = adapter
    }

}