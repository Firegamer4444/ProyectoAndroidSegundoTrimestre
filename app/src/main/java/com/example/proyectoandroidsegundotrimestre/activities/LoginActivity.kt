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
import com.example.proyectoandroidsegundotrimestre.databinding.ActivityLoginBinding
import com.example.proyectoandroidsegundotrimestre.room.DatabaseProvider
import com.example.proyectoandroidsegundotrimestre.room.Usuario
import com.example.proyectoandroidsegundotrimestre.room.UsuarioDao
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val db = DatabaseProvider.getDatabase(this)
        val ususarioDAO = db.userDao()

        binding.loginButton.setOnClickListener{
            val usuario = binding.usuarioEditText.text.toString()
            val contraseña = binding.passwordEditText.text.toString()

            if (contraseña.isNotEmpty() && usuario.isNotEmpty()) {
                lifecycleScope.launch() {
                    if (ususarioDAO.login(usuario, contraseña) != null){
                        // se inicia la mainActivity y se le pasa el usuario
                        var intent = Intent(this@LoginActivity, MainActivity::class.java)

                        val bundle = Bundle()
                        bundle.putString("usuario" , usuario)

                        intent.putExtras(bundle)
                        startActivity(intent)
                        finish()
                    }
                    else {
                        Toast.makeText(this@LoginActivity, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.registerButton.setOnClickListener{
            var intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}