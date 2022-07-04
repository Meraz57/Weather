package com.example.weather.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import xyz.teamprojectx.weather.data.response.todayForecast.ResponseOneCall
import java.util.*

class FindByLocation : Fragment() {
    private var _binding: FragmentFindByLocationBinding? = null
    private val binding get() = _binding!!
    private lateinit var fusedLocationClient:FusedLocationProviderClient
    private val api = RetrofitOpenWeatherClient.apiInterfaceOW
    private val permissionId=2

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

        getLocation()

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

    //API Calling


        @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
                fusedLocationClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
                    val location: Location? = task.result
                    if (location != null) {
                        Log.d("TAG", "getLocation: Location find")
                        val geocoder = Geocoder(requireContext(), Locale.getDefault())
                        val list: List<Address> =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        val address=list[0]

                        handleWeatherData(address.latitude,address.longitude)
                        recyclerViewHandle(address.latitude,address.longitude)



                    }
                }
            } else {

                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }
    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }
    private fun requestPermissions() {

        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionId
        )
    }




    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == permissionId) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            }else{

                handleWeatherData(23.812159879418545, 90.42453827869917)

                recyclerViewHandle(23.812159879418545, 90.42453827869917)

            }
        }
    }



    private fun recyclerViewHandle(lat: Double,lon: Double) {

        api.todayForecast(
            "$lat",
            "$lon",
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

    private fun handleWeatherData(lat:Double,lon:Double) {

        val api = RetrofitOpenWeatherClient.apiInterfaceOW
        api.weather("$lat", "$lon", "e13d7e0ca2e481d477ee300f03e94f3d")
            .enqueue(object :Callback<CurrentWeather> {
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