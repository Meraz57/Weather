package com.example.weather.ui

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.adapter.SevendayInfoAdapter
import com.example.weather.R
import com.example.weather.databinding.FragmentSevenDaysWeatherInfoBinding
import com.example.weather.dataclass.data.currentweather.CurrentWeather
import com.example.weather.network.RetrofitOpenWeatherClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.weather.dataclass.data.nextWeek.ResponseNextWeek

class SevenDaysWeatherInfo : Fragment() {

    private var _binding:FragmentSevenDaysWeatherInfoBinding?=null
    private val binding get() = _binding!!
    private val api=RetrofitOpenWeatherClient.apiInterfaceOW
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
        sevendaysData()


        val drawerLayout=requireActivity().findViewById<DrawerLayout>(R.id.drawerLayout)
        binding.btnDrawer.setOnClickListener {
            if (drawerLayout.isDrawerOpen(Gravity.LEFT)){
                drawerLayout.closeDrawer(GravityCompat.START)
            }else{
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }

    }

    private fun sevendaysData() {
        api.weather("23","90","e13d7e0ca2e481d477ee300f03e94f3d").enqueue(object :Callback<CurrentWeather>{
            override fun onResponse(
                call: Call<CurrentWeather>,
                response: Response<CurrentWeather>
            ) {
                if (response.isSuccessful){
                    Log.d("TAG", "onResponse: ${response.message()}")
                    val data=response.body()?.weather?.get(0)
                    val data2=response.body()

                    binding.apply {
                        weathertxt.text=data?.description.toString()
                        degreebtn.text="${data2?.main?.temp_max.toString()} °"
                        tempMin.text="${data2?.main?.temp_min.toString()} °"

                        humidity.text="${data2?.main?.humidity.toString()}%"
                        windSpeed.text= "${data2?.wind?.speed.toString()}Km/h"
                        changeofRain.text= "${data2?.wind?.gust.toString()}Km/h"
                    }

                }else{
                    Log.d("TAG", "onResponse: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                Log.d("TAG", "onFailure: ${t.message}")
            }

        })
    }

    private fun handleRecyclerview() {
        api.nextWeek("23",
            "90",
            "e13d7e0ca2e481d477ee300f03e94f3d").enqueue(object :Callback<ResponseNextWeek>{
            override fun onResponse(
                call: Call<ResponseNextWeek>,
                response: Response<ResponseNextWeek>
            ) {
                if (response.isSuccessful){
                    val data=response.body()!!.list?.get(0)
                    val mydadapter=SevendayInfoAdapter(data?.main)
                    binding.sevendaysrecyclerview.apply {
                        adapter=mydadapter
                        layoutManager=LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
                    }
                }
            }

            override fun onFailure(call: Call<ResponseNextWeek>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}