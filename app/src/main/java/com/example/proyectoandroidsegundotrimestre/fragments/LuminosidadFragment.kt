package com.example.proyectoandroidsegundotrimestre.fragments

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proyectoandroidsegundotrimestre.R
import com.example.proyectoandroidsegundotrimestre.databinding.FragmentLuminosidadBinding
import com.example.proyectoandroidsegundotrimestre.databinding.FragmentResolucionBinding

class LuminosidadFragment : Fragment() , SensorEventListener {

    private var _binding: FragmentLuminosidadBinding? = null
    private val binding get() = _binding!!
    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLuminosidadBinding.inflate(inflater, container, false)

        // inicializar el sensor de luminosidad
        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)


        return binding.root
    }

    override fun onResume() {
        super.onResume()
        lightSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val lightLevel = it.values[0]
            val status = when {
                lightLevel < 100 -> "Oscuro"
                lightLevel in 100.0..2000.0 -> "Normal"
                else -> "Brillante"
            }
            binding.luminosidadTextView.text = "Luminosidad: $lightLevel lx\nEstado: $status"
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

}