package com.example.weather.ui

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.weather.R
import com.example.weather.databinding.FragmentSearchByDateBinding


class SearchByDate : Fragment() {
    private var _binding:FragmentSearchByDateBinding?=null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       _binding= FragmentSearchByDateBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controlNavigationDrawer()
    }
    private fun controlNavigationDrawer() {

        binding.btnDrawer.setOnClickListener {
            if (binding.drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        binding.btnSearchByDate.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            findNavController().navigate(R.id.action_home_to_searchByDate)
        }

        binding.btnWeatherFormat.setOnClickListener{
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            findNavController().navigate(R.id.action_home_to_weatherFormat)
        }

        binding.btnAboutUs.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            findNavController().navigate(R.id.action_home_to_aboutUs)
        }

        binding.btnSearchByLocation.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            findNavController().navigate(R.id.action_home_to_findByLocation)

        }

    }

    override fun onDestroy() {
        super.onDestroy()

    }

}