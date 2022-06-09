package com.example.weather.ui

import android.graphics.Color
import android.os.Bundle
import android.text.Layout

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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

        binding.btnDrawer.setOnClickListener{

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


    /*  private fun controlNavigationDrawer() {

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


      }*/

    private fun RecyclerViewHandle() {

        val weatherAdapter = AdapterWeather()
        binding.recyclerview.apply {
            adapter=weatherAdapter
            layoutManager=LinearLayoutManager(requireContext())
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