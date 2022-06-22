package com.example.weather.ui

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.adapter.AdapterWeather
import com.example.weather.adapter.NewsAdapter
import com.example.weather.adapter.TabLayoutAdapter
import com.example.weather.databinding.FragmentHomeBinding
import com.example.weather.dataclass.Data
import com.example.weather.network.RetrofitClient
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Home : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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

        newsPostRecyclerview()
        recyclerViewHandle()


        val drawerMenu = requireActivity().findViewById<DrawerLayout>(R.id.drawerLayout)
        binding.btnDrawer.setOnClickListener {
            if (drawerMenu.isDrawerOpen(Gravity.LEFT)) {
                drawerMenu.closeDrawer(GravityCompat.START)
            } else {
                drawerMenu.openDrawer(GravityCompat.START)
            }

        }
        binding.btnCurrentWeather.setOnClickListener {
            var isdetailsweatheropen = false
            isdetailsweatheropen = !isdetailsweatheropen
            binding.detailsCurrentWeather.isVisible = isdetailsweatheropen
        }


    }

    private fun handleBtnClick() {


        binding.btnsevenday.setOnClickListener {
            findNavController().navigate(R.id.sevenDaysWeatherInfo)
        }
    }


    private fun recyclerViewHandle() {

        val weatherAdapter = AdapterWeather()
        binding.recyclerview.apply {
            adapter = weatherAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        }

    }

    private fun newsPostRecyclerview() {
        val api = RetrofitClient.apiInterface

        api.newsPost().enqueue(object : Callback<List<Data>> {
            override fun onResponse(call: Call<List<Data>>, response: Response<List<Data>>) {

                if (response.isSuccessful) {
                    Log.d("TAG", "onResponse:")
                    val news = response.body()
                    val newsAdapter = NewsAdapter(news!!)
                    binding.newsRecycler.adapter = newsAdapter
                    newsAdapter.onItemClickListener(object : NewsAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            findNavController().navigate(R.id.newsViewFragment)

                        }

                    })

                }

            }


            override fun onFailure(call: Call<List<Data>>, t: Throwable) {
                Log.d("TAG", "onFailure: ${t.message}")
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