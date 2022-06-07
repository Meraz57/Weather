package com.example.weather.ui

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.Adapter.AdapterWeather
import com.example.weather.Adapter.NewsAdapter
import com.example.weather.R
import com.example.weather.databinding.FragmentHomeBinding


class Home : Fragment() {

    private var _binding:FragmentHomeBinding?=null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding= FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        RecyclerViewHandle()
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

        binding.btnCurrentWeather.setOnClickListener {
        var isdetailsweatheropen=false
        isdetailsweatheropen=!isdetailsweatheropen
            binding.detailsCurrentWeather.isVisible=isdetailsweatheropen
        }

        binding.btnAboutUs.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            findNavController().navigate(R.id.action_home_to_aboutUs)
        }
        binding.btnSearchByLocation.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            findNavController().navigate(R.id.action_home_to_findByLocation)

        }
        binding.btnFindMyLocation.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            findNavController().navigate(R.id.action_home_to_findMyLocation)
        }
        binding.btnTermsCondition.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            findNavController().navigate(R.id.action_home_to_teamsCondition)
        }

        binding.btnFindMyLocation.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            findNavController().navigate(R.id.action_home_to_findMyLocation)

        }

        binding.btnSubscription.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            findNavController().navigate(R.id.action_home_to_upgradeToPremium)
        }


    }

    private fun RecyclerViewHandle() {

        val weatherAdapter=AdapterWeather()
        binding.recyclerview.adapter=weatherAdapter

        val newsAdapter=NewsAdapter()
        binding.newsRecycler.adapter=newsAdapter

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}