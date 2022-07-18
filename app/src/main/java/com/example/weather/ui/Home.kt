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
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.weather.R
import com.example.weather.adapter.AdapterWeather
import com.example.weather.adapter.NewsAdapter
import com.example.weather.adapter.TabLayoutAdapter
import com.example.weather.databinding.FragmentHomeBinding
import com.example.weather.dataclass.data.ResponseNewsData
import com.example.weather.dataclass.data.currentweather.CurrentWeather
import com.example.weather.network.RetrofitClient
import com.example.weather.network.RetrofitOpenWeatherClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import xyz.teamprojectx.weather.data.response.todayForecast.ResponseOneCall
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class Home : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val api = RetrofitOpenWeatherClient.apiInterfaceOW
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val permissionId = 2


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mSharePrefarence.init(requireContext())
        handleBtnClick()
        setTabLayout()
        getLocation()
        newsPostRecyclerview()


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

    companion object {
        private const val TAG = "Home"
    }


    private fun recyclerViewHandle(lat: Double, lon: Double) {
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

                if (response.isSuccessful) {
                    Log.d(TAG, "onResponse home recycelrview: ${response.message()}")
                    val weatherdata = response.body()

                    Log.d(TAG, "onResponse home recycelrview: ${weatherdata?.hourly}")
                    val weatherAdapter = weatherdata!!.hourly?.let { AdapterWeather(it) }
                    binding.recyclerview.apply {
                        adapter = weatherAdapter
                        layoutManager =
                            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
                    }

                } else {
                    Log.d(TAG, "onResponse: ${response.errorBody()?.string()}")
                }
                ///ene else nei keno
            }

            override fun onFailure(call: Call<ResponseOneCall>, t: Throwable) {
                Log.d(TAG, "onFailure home recyclerview: ${t.message}")
            }

        })


    }

    private fun newsPostRecyclerview() {
        val api = RetrofitClient.apiInterface

        api.newsPost().enqueue(object : Callback<ResponseNewsData> {
            override fun onResponse(
                call: Call<ResponseNewsData>,
                response: Response<ResponseNewsData>
            ) {

                if (response.isSuccessful) {
                    Log.d("TAG", "onResponse:Success")
                    val news = response.body()
                    val newsAdapter = NewsAdapter(news!!.data)
                    binding.newsRecycler.adapter = newsAdapter
                    newsAdapter.onItemClickListener(object : NewsAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            findNavController().navigate(R.id.newsViewFragment)

                        }

                    })

                }

            }


            override fun onFailure(call: Call<ResponseNewsData>, t: Throwable) {
                Log.d("TAG", "onFailure: ${t.message}")
            }

        })
    }

    // set tablayout
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
//                1 -> {
//                    tab.text = resources.getString(R.string.tomorrow)
//                }
            }

        }.attach()

    }

    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLocation() {

        Log.d(TAG, "getLocation: 1st")

        if (checkPermissions()) {
            Log.d(TAG, "getLocation: 2nd")
            if (isLocationEnabled()) {
                Log.d(TAG, "getLocation: 3rd")
                fusedLocationClient =
                    LocationServices.getFusedLocationProviderClient(requireActivity())
                fusedLocationClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
                    val location: Location? = task.result
                    Log.d(TAG, "getLocation: 4th $location")
                    if (location != null) {

                        Log.d(TAG, "getLocation: 5th")
                        Log.d("TAG", "getLocation: Location find")
                        val geocoder = Geocoder(requireContext(), Locale.getDefault())
                        val list: List<Address> =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        val address = list[0]

                        binding.currentplaceid.text = "${address.locality},${address.countryName} "
                        Log.d(TAG, "getLocation: Got permission")
                        recyclerViewHandle(address.latitude, address.longitude)
                        currentWeatherData(address.latitude, address.longitude)



                    } else {
                        dummyLocation()


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
            } else {

                Log.d(TAG, "onRequestPermissionsResult: permison not got")
                dummyLocation()

            }
        }
    }

    private fun dummyLocation() {
        currentWeatherData(23.812159879418545, 90.42453827869917)
        recyclerViewHandle(23.812159879418545, 90.42453827869917)
    }


    private fun currentWeatherData(lat: Double, lon: Double) {

        val api = RetrofitOpenWeatherClient.apiInterfaceOW
        api.weather("$lat", "$lon","metric", "e13d7e0ca2e481d477ee300f03e94f3d")
            .enqueue(object : Callback<CurrentWeather> {
                @SuppressLint("SetTextI18n")
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onResponse(
                    call: Call<CurrentWeather>,
                    response: Response<CurrentWeather>
                ) {
                    if (response.isSuccessful) {
                        Log.d("TAG", "onResponseCurrentWeatherSuccess:")
                        val data = response.body()!!
                        val list = response.body()!!.weather?.get(0)



                        binding.apply {

                            weatherimage.load("http://openweathermap.org/img/wn/${data.weather?.getOrNull(0)?.icon?:""}@2x.png")
                            time.text = data.dt?.toLong()?.toDate().toString()
                            if ( mSharePrefarence.getTemperatureUnit() == mSharePrefarence.TEMPERATURE_CELSIUS) {
                                temperature.text=data.main!!.temp!!.toInt().toString()
                            } else {
                                temperature.text = data.main?.temp!!.toDouble().toFahrenheit()
                            }

                            val windValue = data.wind?.speed
                            wind.text = when (mSharePrefarence.getWindFormat()) {
                                mSharePrefarence.WIND_MS -> {
                                    windValue.toString() + " m/s"
                                }
                                mSharePrefarence.WIND_KMH -> {
                                    windValue?.toKmHr() + " Km/h"
                                }
                                mSharePrefarence.WIND_MPH -> {
                                    windValue?.toMph() + " mph"
                                }
                                else -> {
                                    ""
                                }
                            }

                            humidity.text = "${data.main.humidity.toString()}%"
                            changeofRain.text = data.wind?.gust.toString()
                            weathertxt.text = list?.description.toString()

                            //start details about current weather
                            if (mSharePrefarence.getTemperatureUnit()==mSharePrefarence.TEMPERATURE_CELSIUS){
                                binding.temperaturehome.text =data.main.temp!!.toInt().toString()
                            }else{
                                binding.temperaturehome.text=data.main.temp!!.toDouble().toFahrenheit()
                            }

                            if (mSharePrefarence.getTemperatureUnit()==mSharePrefarence.TEMPERATURE_CELSIUS){
                                binding.feelsLike.text =data.main.feels_like?.toInt().toString()
                            }else{
                                binding.feelsLike.text=data.main.feels_like?.toDouble()?.toFahrenheit()
                            }

                            if (mSharePrefarence.getTemperatureUnit()==mSharePrefarence.TEMPERATURE_CELSIUS){
                                binding.uvIndex.text =data.main.temp_max?.toInt().toString()
                            }else{
                                binding.uvIndex.text =data.main.temp_max?.toDouble()?.toFahrenheit()
                            }

                            binding.visivility.text = data.visibility.toString()
                            binding.pressure.text = data.main.pressure.toString()

                            currentDate.text=data.dt?.toLong()?.toOnlyDate()


                        }


                    } else {
                        Log.d(
                            "TAG",
                            "onResponseCurrentWeather: ${
                                response.errorBody()?.string()
                            }  ${response.message()}"
                        )

                    }
                }

                override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                    Log.d("TAG", "onFailureCurrentWeather: ${t.message} ")
                }

            })
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}