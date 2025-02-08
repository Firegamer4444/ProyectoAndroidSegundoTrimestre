package com.example.proyectoandroidsegundotrimestre.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proyectoandroidsegundotrimestre.R
import com.example.proyectoandroidsegundotrimestre.databinding.FragmentResolucionBinding
import com.example.proyectoandroidsegundotrimestre.databinding.ReseniaDialogBinding


class ResolucionFragment : Fragment() {

    private var _binding: FragmentResolucionBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResolucionBinding.inflate(inflater, container, false)

        // detectar la resolución de la pantalla
        val displayMetrics = resources.displayMetrics
        val width = displayMetrics.widthPixels
        val height = displayMetrics.heightPixels

        binding.resolucionTextView.text = "Resolución de la pantalla: $width x $height"

        return binding.root
    }


}