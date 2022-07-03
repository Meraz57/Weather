package com.example.weather.ui

import android.Manifest
import android.annotation.SuppressLint
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
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*

class SevenDaysWeatherInfo : Fragment() {

    private var _binding:FragmentSevenDaysWeatherInfoBinding?=null
    private val binding get() = _binding!!
    private val api=RetrofitOpenWeatherClient.apiInterfaceOW
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val permissionId = 2

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

        getLocation()

        val drawerLayout=requireActivity().findViewById<DrawerLayout>(R.id.drawerLayout)
        binding.btnDrawer.setOnClickListener {
            if (drawerLayout.isDrawerOpen(Gravity.LEFT)){
                drawerLayout.closeDrawer(GravityCompat.START)
            }else{
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }

    }



        //permission check and API Calling




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
                            apiCall(address.latitude,address.longitude)

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
                apiCall(23.812159879418545, 90.42453827869917)
            }
        }
    }


    private fun apiCall(lat:Double,long:Double){
        api.weather("$lat","$long","e13d7e0ca2e481d477ee300f03e94f3d").enqueue(object :Callback<CurrentWeather>{
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