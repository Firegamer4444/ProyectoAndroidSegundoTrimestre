package com.example.proyectoandroidsegundotrimestre.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.proyectoandroidsegundotrimestre.R
import com.example.proyectoandroidsegundotrimestre.databinding.ActivityRegisterBinding
import com.example.proyectoandroidsegundotrimestre.room.AppDatabase
import com.example.proyectoandroidsegundotrimestre.room.DatabaseProvider
import com.example.proyectoandroidsegundotrimestre.room.Usuario
import com.example.proyectoandroidsegundotrimestre.room.UsuarioDao
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var db: AppDatabase
    private lateinit var ususarioDAO: UsuarioDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        db = DatabaseProvider.getDatabase(this)
        ususarioDAO = db.userDao()


        binding.registerButton.setOnClickListener{

            val usuario = binding.usuarioEditText.text.toString()
            val contraseña = binding.passwordEditText.text.toString()
            val contraseñaRepetida = binding.repeatPasswordEditText.text.toString()

            if (contraseña.equals(contraseñaRepetida) && contraseña.isNotEmpty() && usuario.isNotEmpty()) {
                register(usuario , contraseña)
                Toast.makeText(applicationContext, "Registro hecho con exito", Toast.LENGTH_LONG).show()
                // se inicia la mainActivity y se le pasa el usuario
                var intent = Intent(this@RegisterActivity, MainActivity::class.java)

                val bundle = Bundle()
                bundle.putString("usuario" , usuario)

                intent.putExtras(bundle)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(applicationContext, "Email o contraseñas invalidas", Toast.LENGTH_LONG).show()
            }
        }

        binding.loginButton.setOnClickListener{
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }



    private fun register(usuario: String, contraseña: String) {
        lifecycleScope.launch {
            ususarioDAO.insert(Usuario(usuario = usuario, contraseña = contraseña))
        }
    }
}