package com.example.proyectoandroidsegundotrimestre.activities

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectoandroidsegundotrimestre.R
import com.example.proyectoandroidsegundotrimestre.databinding.ActivityAudioRecorderBinding
import java.io.IOException

class GrabadoraActivity : BaseActivity() {


    lateinit var binding: ActivityAudioRecorderBinding

    // Instancias de MediaRecorder y MediaPlayer para grabar y reproducir audio
    private var mediaRecorder: MediaRecorder? = null
    private var mediaPlayer: MediaPlayer? = null

    // Bandera para saber si estamos grabando
    private var isAudioPlaying = false

    // Bandera para saber si se esta reproduciondo el audio
    private var isRecording = false

    // Nombre y ubicación del archivo donde se guardará la grabación
    private var fileName: String? = null

    // Constantes para el código de solicitud de permisos y los permisos requeridos
    private val REQUEST_RECORD_AUDIO_PERMISSION = 200
    private val permissionsRequired = arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAudioRecorderBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.grabadora)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setUpNavigatorDrawer(binding.drawerLayout , binding.navigationView)

        setSupportActionBar(binding.toolbarLayout.toolbar)
        supportActionBar?.title = "Grabadora de audio"

        // Establecemos la ruta y el nombre del archivo donde se grabará el audio
        fileName = "${externalCacheDir?.absolutePath}/grabacion.3gp"

        // Verificamos si tenemos los permisos necesarios para grabar y acceder al almacenamiento
        checkPermissions()

        // Configuramos el botón de grabar
        binding.grabarButton.setOnClickListener {
            if (isRecording) {
                // Si estamos grabando, detenemos la grabación
                stopRecording()
                // Cambiamos el texto del botón a "Grabar"
                binding.grabarButton.text = "Grabar"
            } else {
                // Si no estamos grabando, comenzamos la grabación
                startRecording()
                // Cambiamos el texto del botón a "Detener"
                binding.grabarButton.text = "Detener"
            }
        }

        // Configuramos el botón de reproducir
        binding.reproducirButton.setOnClickListener {
            if (fileName != null) {
                if (isAudioPlaying) {
                    // Si se esta reproduciondo se detiene
                    stopPlaying()
                    // Se cambia el icono
                    binding.reproducirButton.setImageResource(android.R.drawable.ic_media_play)
                }
                else {
                    // Si existe un archivo grabado, lo reproducimos
                    startPlaying()
                    // Se cambia el icono
                    binding.reproducirButton.setImageResource(android.R.drawable.ic_media_pause)
                }
            } else {
                // Si no hay archivo grabado, mostramos un mensaje de error
                Toast.makeText(this, "No hay archivo de audio grabado.", Toast.LENGTH_SHORT).show()
            }
        }

        // configuramos el listener de la seekbar
        binding.audioSeekBar.setOnSeekBarChangeListener(object : android.widget.SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: android.widget.SeekBar?, progress: Int, fromUser: Boolean) {
                if (isAudioPlaying) {
                    mediaPlayer?.setVolume(progress/100f, progress/100f)
                }
            }

            override fun onStartTrackingTouch(seekBar: android.widget.SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: android.widget.SeekBar?) {
            }
        })

        binding.velocidadSeekBar.setOnSeekBarChangeListener(object : android.widget.SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: android.widget.SeekBar?, progress: Int, fromUser: Boolean) {
                if (isAudioPlaying) {
                    mediaPlayer?.playbackParams?.setSpeed(progress/100f)
                }
            }

            override fun onStartTrackingTouch(seekBar: android.widget.SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: android.widget.SeekBar?) {
            }
        })

    }


    // Método para verificar si se tienen los permisos requeridos
    private fun checkPermissions() {
        // Verificamos si alguno de los permisos necesarios no ha sido concedido
        if (permissionsRequired.any { ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED }) {
            // Si algún permiso no ha sido concedido, pedimos los permisos
            ActivityCompat.requestPermissions(this, permissionsRequired, REQUEST_RECORD_AUDIO_PERMISSION)
        }
    }

    // Método para iniciar la grabación de audio
    private fun startRecording() {
        // Creamos un objeto MediaRecorder para grabar el audio
        mediaRecorder = MediaRecorder().apply {
            // Establecemos la fuente de audio (micrófono)
            setAudioSource(MediaRecorder.AudioSource.MIC)
            // Establecemos el formato de salida del archivo (en este caso, .3gp)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            // Establecemos el codificador de audio
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            // Establecemos el archivo de salida donde se guardará la grabación
            setOutputFile(fileName)
        }

        try {
            // Preparamos y comenzamos la grabación
            mediaRecorder?.prepare()
            mediaRecorder?.start()
            isRecording = true
            // Mostramos un mensaje informando que se está grabando
            Toast.makeText(this, "Grabando...", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            // Si ocurre un error al preparar o comenzar la grabación, lo mostramos
            e.printStackTrace()
            Toast.makeText(this, "Error al grabar audio.", Toast.LENGTH_SHORT).show()
        }
    }

    // Método para detener la grabación de audio
    private fun stopRecording() {
        mediaRecorder?.apply {
            // Detenemos y liberamos el MediaRecorder
            stop()
            release()
        }
        mediaRecorder = null
        isRecording = false
        // Mostramos un mensaje indicando que la grabación se ha detenido
        Toast.makeText(this, "Grabación detenida", Toast.LENGTH_SHORT).show()
    }

    // Método para comenzar a reproducir la grabación de audio
    private fun startPlaying() {
        // Creamos un objeto MediaPlayer para reproducir el audio
        mediaPlayer = MediaPlayer().apply {
            try {
                // Establecemos el archivo de audio que vamos a reproducir
                setDataSource(fileName)
                // Preparamos el MediaPlayer
                prepare()
                // se le asigna el volumen y la velocidad
                setVolume(binding.audioSeekBar.progress/100f , binding.audioSeekBar.progress/100f);
                playbackParams.setSpeed(binding.audioSeekBar.progress/100f);
                // Comenzamos a reproducir el audio
                start()
                // Mostramos un mensaje indicando que el audio está reproduciéndose
                Toast.makeText(this@GrabadoraActivity, "Reproduciendo audio...", Toast.LENGTH_SHORT).show()

                isAudioPlaying = true
            } catch (e: IOException) {
                // Si ocurre un error al reproducir el audio, lo mostramos
                e.printStackTrace()
                Toast.makeText(this@GrabadoraActivity, "Error al reproducir audio.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Método para parar la reproducion de la grabación de audio
    private fun stopPlaying() {
        mediaPlayer?.apply {
            // Detenemos y liberamos el MediaRecorder
            stop()
            release()
        }
        mediaPlayer = null
        isAudioPlaying = false
        // Mostramos un mensaje indicando que la reproduccion del audio se ha detenido
        Toast.makeText(this, "Reproduccion del audio detenida", Toast.LENGTH_SHORT).show()
    }


    // Método que se ejecuta cuando la actividad se detiene o sale de la pantalla
    override fun onStop() {
        super.onStop()
        // Liberamos los recursos del MediaRecorder y el MediaPlayer para evitar fugas de memoria
        mediaRecorder?.release()
        mediaPlayer?.release()
    }

}