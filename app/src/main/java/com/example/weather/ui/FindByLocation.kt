package com.example.weather.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.adapter.AdapterWeather
import com.example.weather.databinding.FragmentFindByLocationBinding
import com.example.weather.dataclass.data.currentweather.CurrentWeather
import com.example.weather.network.RetrofitOpenWeatherClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import xyz.teamprojectx.weather.data.response.todayForecast.ResponseOneCall

class FindByLocation : Fragment() {
    private var _binding: FragmentFindByLocationBinding? = null
    private val binding get() = _binding!!
    private val api = RetrofitOpenWeatherClient.apiInterfaceOW

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFindByLocationBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewHandle()
        handleWeatherData()

        binding.btnsevenday.setOnClickListener {
            findNavController().navigate(R.id.sevenDaysWeatherInfo)
        }
        val drawerLayout = requireActivity().findViewById<DrawerLayout>(R.id.drawerLayout)
        binding.btnDrawer.setOnClickListener {
            if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
    }

    private fun recyclerViewHandle() {

        api.todayForecast(
            "23",
            "90",
            "current,minutely,daily,alerts",
            "e13d7e0ca2e481d477ee300f03e94f3d"
        ).enqueue(object : Callback<ResponseOneCall> {
            override fun onResponse(
                call: Call<ResponseOneCall>,
                response: Response<ResponseOneCall>
            ) {
                if (response.isSuccessful){
                    Log.d(TAG, "onResponse: ${response.message()}")
                    val weatherdata=response.body()
                    val weatherAdapter= weatherdata!!.hourly?.let { AdapterWeather(it) }
                    binding.recyclerview.apply {
                        adapter=weatherAdapter
                        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)


                    }

                }
            }

            override fun onFailure(call: Call<ResponseOneCall>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })




    }

    private fun handleWeatherData() {

        val api = RetrofitOpenWeatherClient.apiInterfaceOW
        api.weather("23", "90", "e13d7e0ca2e481d477ee300f03e94f3d")
            .enqueue(object : retrofit2.Callback<CurrentWeather> {
                override fun onResponse(
                    call: Call<CurrentWeather>,
                    response: Response<CurrentWeather>
                ) {
                    if (response.isSuccessful) {
                        Log.d(TAG, "onResponse: ${response.message()}")

                        val data = response.body()!!

                        binding.apply {

                            sunriseTime.text = data.sys?.sunrise.toString()
                            sunsetTime.text = data.sys?.sunset.toString()
                            temperature.text=data.main?.temp.toString()
                            address.text=data.name
                            location.text=data.name
                            location1.text=data.name





                        }

                    }

                }

                override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message}")
                }

            })

    }


}