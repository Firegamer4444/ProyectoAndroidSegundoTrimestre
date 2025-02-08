package com.example.proyectoandroidsegundotrimestre.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectoandroidsegundotrimestre.R
import com.example.proyectoandroidsegundotrimestre.adapters.ViewPagerAdapter
import com.example.proyectoandroidsegundotrimestre.databinding.ActivityMainBinding
import com.example.proyectoandroidsegundotrimestre.databinding.ActivityMiscBinding
import com.google.android.material.tabs.TabLayoutMediator

class FuncionalidadesVariasActivity : BaseActivity(){

    private lateinit var binding: ActivityMiscBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMiscBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.misc)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setUpNavigatorDrawer(binding.drawerLayout , binding.navigationView)

        setSupportActionBar(binding.toolbarLayout.toolbar)
        supportActionBar?.title = "Funcionalidades varias"

        // se añade el adaptador al viewpager
        binding.viewPager.adapter = ViewPagerAdapter(this)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Resolución"
                }
                1 -> {
                    tab.text = "Broadcast"
                }
                2 -> {
                    tab.text = "Luminosidad"
                }
                3 -> {
                    tab.text = "Localización"
                }
            }
        }.attach()

    }

}