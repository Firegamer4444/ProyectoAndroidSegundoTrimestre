package com.example.proyectoandroidsegundotrimestre.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.proyectoandroidsegundotrimestre.R
import com.example.proyectoandroidsegundotrimestre.databinding.FragmentBroadcastBinding
import com.example.proyectoandroidsegundotrimestre.databinding.FragmentResolucionBinding


class BroadcastFragment : Fragment() {

    private lateinit var receiver: BroadcastReceiver
    private var isReceiverRegistered = false
    private var _binding: FragmentBroadcastBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBroadcastBinding.inflate(inflater, container, false)

        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                when (intent?.action) {
                    Intent.ACTION_POWER_CONNECTED -> Toast.makeText(requireContext(), "Cargador Conectado", Toast.LENGTH_SHORT).show()
                    Intent.ACTION_POWER_DISCONNECTED -> Toast.makeText(requireContext(), "Cargador Desconectado", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.broadcastButton.setOnClickListener {
            if (!isReceiverRegistered) {
                val intentFilter = IntentFilter().apply {
                    addAction(Intent.ACTION_POWER_CONNECTED)
                    addAction(Intent.ACTION_POWER_DISCONNECTED)
                }
                requireContext().registerReceiver(receiver, intentFilter)
                isReceiverRegistered = true
                binding.broadcastButton.text = "Desactivar broadcast receiver"
            } else {
                requireContext().unregisterReceiver(receiver)
                isReceiverRegistered = false
                binding.broadcastButton.text = "Activar broadcast receiver"
            }
        }

        return binding.root
    }


}