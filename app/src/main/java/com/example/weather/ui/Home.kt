package com.example.weather.ui

import android.graphics.Color
import android.os.Bundle
import android.text.Layout
import android.view.Gravity

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.Adapter.AdapterWeather
import com.example.weather.Adapter.NewsAdapter
import com.example.weather.Adapter.TabLayoutAdapter
import com.example.weather.MainActivity
import com.example.weather.R
import com.example.weather.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator


class Home : Fragment(){

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleBtnClick()
        setTabLayout()
        RecyclerViewHandle()
        val drawerMenu=requireActivity().findViewById<DrawerLayout>(R.id.drawerLayout)
        binding.btnDrawer.setOnClickListener{
        if (drawerMenu.isDrawerOpen(Gravity.LEFT)){
            drawerMenu.closeDrawer(GravityCompat.START)
        }else{
            drawerMenu.openDrawer(GravityCompat.START)
        }

        }
        binding.btnCurrentWeather.setOnClickListener {
            var isdetailsweatheropen=false
            isdetailsweatheropen=!isdetailsweatheropen
            binding.detailsCurrentWeather.isVisible=isdetailsweatheropen
        }



    }

    private fun handleBtnClick() {


       binding.btnsevenday.setOnClickListener {
           findNavController().navigate(R.id.sevenDaysWeatherInfo)
       }
    }



    private fun RecyclerViewHandle() {

        val weatherAdapter = AdapterWeather()
        binding.recyclerview.apply {
            adapter=weatherAdapter
            layoutManager=LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
        }


        val newsAdapter = NewsAdapter()
        binding.newsRecycler.adapter = newsAdapter
        newsAdapter.onItemClickListener(object :NewsAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                findNavController().navigate(R.id.newsViewFragment)

            }

        })

    }


    private fun setTabLayout() {

        val tabAdapter = TabLayoutAdapter(childFragmentManager, lifecycle)
        binding.viewPager2.adapter = tabAdapter
        TabLayoutMediator(
            binding.tabLayout, binding.viewPager2
        ) { tab, position ->

            when (position) {
                0 -> {
                    tab.text = resources.getString(R.string.today)
                }
                1 -> {
                    tab.text = resources.getString(R.string.tomorrow)
                }
            }

        }.attach()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}