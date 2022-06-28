package com.example.weather.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.adapter.AdapterWeather
import com.example.weather.R
import com.example.weather.databinding.FragmentFindMyLocationBinding
import com.example.weather.dataclass.data.currentweather.CurrentWeather
import com.example.weather.network.RetrofitOpenWeatherClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import xyz.teamprojectx.weather.data.response.nextWeek.ResponseNextWeek
import xyz.teamprojectx.weather.data.response.todayForecast.ResponseOneCall


class FindMyLocation : Fragment() {


    private var _binding: FragmentFindMyLocationBinding? = null
    private val binding get() = _binding!!
    private val api = RetrofitOpenWeatherClient.apiInterfaceOW


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


        recyclerViewHandle()
        handleBtnClick()
        handleWeather()
        handleDatabinding()


        val drawerLayout=requireActivity().findViewById<DrawerLayout>(R.id.drawerLayout)
        binding.btnDrawer.setOnClickListener {
            if (drawerLayout.isDrawerOpen(Gravity.LEFT)){
                drawerLayout.closeDrawer(GravityCompat.START)
            }else{
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }

    }

    private fun handleWeather() {
        val api=RetrofitOpenWeatherClient.apiInterfaceOW
        api.weather("23","90","e13d7e0ca2e481d477ee300f03e94f3d").enqueue(object : Callback<CurrentWeather>{
            override fun onResponse(
                call: Call<CurrentWeather>,
                response: Response<CurrentWeather>
            ) {
                if(response.isSuccessful){
                    Log.d(TAG, "onResponse: ")
                }
            }

            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }


    private fun handleDatabinding(){

        api.weather("23","90","e13d7e0ca2e481d477ee300f03e94f3d").enqueue(object :Callback<CurrentWeather>{
            override fun onResponse(
                call: Call<CurrentWeather>,
                response: Response<CurrentWeather>
            ) {
                if (response.isSuccessful){
                    Log.d(TAG, "onResponse: ${response.message()}")
                    val data= response.body()
                    val list=response.body()?.weather?.get(0)
                    binding.apply {

                        temperature.text=data?.main?.temp.toString()
                        temperatureCondition.text=list?.description
                        address.text=data?.name
                        lastUpdate.text= "Last Updated :${data?.timezone}"

                        sunriseTime.text= "${data?.sys?.sunrise}"
                        sunsetTime.text="${data?.sys?.sunset}"




                    }

                }else{
                    Log.d(TAG, "onResponse: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })


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