package com.example.weather

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.adapter.AdapterWeather
import com.example.weather.databinding.FragmentWeatherBinding
import com.example.weather.dataclass.data.currentweather.CurrentWeather
import com.example.weather.dataclass.data.currentweather.Weather
import com.example.weather.network.RetrofitOpenWeatherClient
import com.example.weather.ui.Home
import com.google.android.gms.location.FusedLocationProviderClient
import retrofit2.Call
import retrofit2.Response
import xyz.teamprojectx.weather.data.response.todayForecast.ResponseOneCall
import javax.security.auth.callback.Callback


class WeatherFragment : Fragment() {
    val api =RetrofitOpenWeatherClient.apiInterfaceOW
     private var param1: String? = null
    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!
       private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       _binding= FragmentWeatherBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

    override fun onResume() {
        super.onResume()

         when (param1) {
            "today" -> {

                handleTodayAndTomorrow(0)

            }
            "tomorrow" -> {
                handleTodayAndTomorrow(1)
            }

            else -> {

            }


        }

    }


    private fun handleTodayAndTomorrow(number: Int) {


    }


}