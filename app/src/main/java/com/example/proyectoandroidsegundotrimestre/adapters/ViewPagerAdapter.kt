package com.example.proyectoandroidsegundotrimestre.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.proyectoandroidsegundotrimestre.fragments.BroadcastFragment
import com.example.proyectoandroidsegundotrimestre.fragments.LocalizacionFragment
import com.example.proyectoandroidsegundotrimestre.fragments.LuminosidadFragment
import com.example.proyectoandroidsegundotrimestre.fragments.ResolucionFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity){
    override fun getItemCount() = 4


    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> ResolucionFragment()
            1 -> BroadcastFragment()
            2 -> LuminosidadFragment()
            3 -> LocalizacionFragment()
            else -> ResolucionFragment()
        }
    }
}