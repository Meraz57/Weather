package com.example.weather.ui

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import com.example.weather.Adapter.AdapterWeather
import com.example.weather.R
import com.example.weather.databinding.FragmentFindMyLocationBinding


class FindMyLocation : Fragment() {


    private var _binding: FragmentFindMyLocationBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFindMyLocationBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleRecyclerview()
        handleBtnClick()
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
        val weatherAdapter=AdapterWeather()
        binding.recyclerview.adapter=weatherAdapter

    }
    private fun handleBtnClick(){
        binding.next7days.setOnClickListener {
            findNavController().navigate(R.id.sevenDaysWeatherInfo)
        }
    }




        override fun onDestroy() {
            super.onDestroy()
            _binding = null
        }
    }