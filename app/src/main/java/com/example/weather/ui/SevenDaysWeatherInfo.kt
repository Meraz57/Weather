package com.example.weather.ui

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.weather.adapter.SevendayInfoAdapter
import com.example.weather.R
import com.example.weather.databinding.FragmentSevenDaysWeatherInfoBinding

class SevenDaysWeatherInfo : Fragment() {

    private var _binding:FragmentSevenDaysWeatherInfoBinding?=null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentSevenDaysWeatherInfoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleRecyclerview()
        val drawerLayout=requireActivity().findViewById<DrawerLayout>(R.id.drawerLayout)
        binding.btnDrawer.setOnClickListener {
            if (drawerLayout.isDrawerOpen(Gravity.LEFT)){
                drawerLayout.closeDrawer(GravityCompat.START)
            }else{
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }

    }

    private fun handleRecyclerview(){
        val adapter= SevendayInfoAdapter()
        binding.sevendaysrecyclerview.adapter=adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}