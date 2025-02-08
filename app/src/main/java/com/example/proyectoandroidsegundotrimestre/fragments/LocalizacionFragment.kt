package com.example.proyectoandroidsegundotrimestre.fragments

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.proyectoandroidsegundotrimestre.R
import com.example.proyectoandroidsegundotrimestre.databinding.FragmentLocalizacionBinding
import com.example.proyectoandroidsegundotrimestre.databinding.FragmentLuminosidadBinding

class LocalizacionFragment : Fragment() {

    private var _binding: FragmentLocalizacionBinding? = null
    private val binding get() = _binding!!
    private lateinit var locationManager: LocationManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLocalizacionBinding.inflate(inflater, container, false)

        locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        checkLocationPermission()

        return binding.root
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                getLastKnownLocation()
            } else {
                binding.localizacionTextView.text = "Permiso de ubicación denegado."
            }
        }

    private fun checkLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED -> {
                getLastKnownLocation()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                binding.localizacionTextView.text = "Se necesita permiso de ubicación para mostrar la ubicación."
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    private fun getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        location?.let {
            binding.localizacionTextView.text = "Latitud: ${it.latitude}\nLongitud: ${it.longitude}"
        } ?: run {
            binding.localizacionTextView.text = "No se pudo obtener la ubicación."
        }
    }

}