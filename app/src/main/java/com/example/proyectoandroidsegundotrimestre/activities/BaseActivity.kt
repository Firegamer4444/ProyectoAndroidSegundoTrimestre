package com.example.proyectoandroidsegundotrimestre.activities

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.proyectoandroidsegundotrimestre.R
import com.google.android.material.navigation.NavigationView

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Infla el menu de la toolbar
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    // maneja la seleccion del menu de la toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.retrocesoOpcion -> {
                finish()
                true
            }
            R.id.paginaPrincipalOpcion -> {
                navigateToReseñas()
                true
            }
            R.id.grabadoraAudioOpcion -> {
                navigateToGrabadora()
                true
            }
            R.id.funcionalidadesVariasOpcion -> {
                navigateToFuncionalidadesVarias()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun navigateToReseñas() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToGrabadora() {
        val intent = Intent(this, GrabadoraActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToFuncionalidadesVarias() {
        val intent = Intent(this, FuncionalidadesVariasActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }


    // inicializa el drawerLayout y el navigationView
    protected fun setUpNavigatorDrawer(drawerLayout: DrawerLayout, navigationView: NavigationView){

        navigationView.bringToFront();

        // Manejar las selecciones del menú
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_help -> {
                    mostrarDialogoAcercaDe()
                }
                R.id.nav_logout -> {
                    navigateToLogin()
                }
                R.id.nav_exit -> {
                    finishAffinity()
                }
            }
            // Cerrar el panel después de seleccionar una opción
            drawerLayout.closeDrawers()
            true
        }
    }


    // muestra el dialogo de la informacion de la aplicacion
    private fun mostrarDialogoAcercaDe(){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Nombre app: Proyecto android segundo trimestre\nVersion: 1.0\nDesarollador: Diego García Hernández\n")
            .setTitle("Acerca de")
            .setPositiveButton("Cerrar",
                DialogInterface.OnClickListener { dialog, which -> })
        val dialogo = builder.create()
        dialogo.show()
    }
}