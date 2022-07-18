package com.example.weather.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context.MODE_PRIVATE
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.jdrodi.utilities.showPermissionsAlert
import com.example.weather.R
import com.example.weather.adapter.AdapterWeather
import com.example.weather.databinding.FragmentFindMyLocationBinding
import com.example.weather.dataclass.data.currentweather.CurrentWeather
import com.example.weather.network.RetrofitOpenWeatherClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.weather.dataclass.data.location.ResponseLocation
import xyz.teamprojectx.weather.data.response.todayForecast.ResponseOneCall


class FindMyLocation : Fragment() {


    private var _binding: FragmentFindMyLocationBinding? = null
    private val binding get() = _binding!!
    private val api = RetrofitOpenWeatherClient.apiInterfaceOW
    private val permissionId = 2
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val locationPermission = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFindMyLocationBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mSharePrefarence.init(requireContext())

        if (mSharePrefarence.getTemperatureUnit()==mSharePrefarence.TEMPERATURE_CELSIUS){
            binding.temperatureType.text="C"
        }else{
            binding.temperatureType.text="F"
        }

        binding.next7days.setOnClickListener {
            findNavController().navigate(R.id.sevenDaysWeatherInfo)
        }

        binding.searchEt.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val key = binding.searchEt.text.toString()
                if (key.isNotEmpty()) {
                    api.searchLocation("$key", 5, "e13d7e0ca2e481d477ee300f03e94f3d")
                        .enqueue(object : Callback<ResponseLocation> {
                            @SuppressLint("SetTextI18n")
                            override fun onResponse(
                                call: Call<ResponseLocation>,
                                response: Response<ResponseLocation>
                            ) {
                                if (response.isSuccessful) {
                                    Log.d(TAG, "onResponse: Find")
                                    val address = response.body()?.get(0)

                                    binding.apply {
                                        if (response.isSuccessful){
                                            layoutCurrentLocation.isVisible=true
                                        }
                                        currentLocation.text =
                                            "${address?.name},${address?.country}"
                                        currentLocation.setOnClickListener {
                                        handleDatabinding("${address?.lat}" ,"${address?.lon}")
                                        recyclerViewHandle("${address?.lat}","${address?.lon}")
                                            address3.text = "${address?.name},${address?.country} "
                                        }





                                    }
                                } else {
                                    Log.d(TAG, "onResponse: ${response.errorBody().toString()}")
                                }
                            }

                            override fun onFailure(call: Call<ResponseLocation>, t: Throwable) {
                                Log.d(TAG, "onFailure: ${t.message}")
                            }


                        })

                } else {
                    requireContext().toast("Please Write Something to Search")
                }
            }
            true
        }

        binding.btnSearch.setOnClickListener {

            val key = binding.searchEt.text.toString()
            if (key.isNotEmpty()) {
                api.searchLocation(key, 5, "e13d7e0ca2e481d477ee300f03e94f3d").enqueue(object : Callback<ResponseLocation> {
                    @SuppressLint("SetTextI18n")
                    override fun onResponse(
                        call: Call<ResponseLocation>,
                        response: Response<ResponseLocation>
                    ) {
                        if (response.isSuccessful) {
                            Log.d(TAG, "onResponse: Find")
                            val address = response.body()?.get(0)

                            binding.apply {
                                if (response.isSuccessful){
                                    layoutCurrentLocation.isVisible=true
                                }
                                currentLocation.text =
                                    "${address?.name},${address?.country}"
                                currentLocation.setOnClickListener {

                                    handleDatabinding("${address?.lat}" ,"${address?.lon}")
                                    recyclerViewHandle("${address?.lat}","${address?.lon}")
                                    address3.text = "${address?.name},${address?.country} "
                                }





                            }
                        } else {
                            Log.d(TAG, "onResponse: ${response.errorBody().toString()}")
                        }
                    }

                    override fun onFailure(call: Call<ResponseLocation>, t: Throwable) {
                        Log.d(TAG, "onFailure: ${t.message}")
                    }


                })


            } else {
                requireContext().toast("Please Write Something to Search")

            }

        }

        binding.layoutCurrentLocation.setOnClickListener {
            checkPermission()
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

// check user location permission
    private fun checkPermission() {
        Dexter.withContext(requireContext())
            .withPermissions(*locationPermission)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {
                        when {
                            report.areAllPermissionsGranted() -> {

                                currentLocation()
                            }
                            report.isAnyPermissionPermanentlyDenied -> {
                                requireContext().showPermissionsAlert(
                                    permission_msg = "For your location weather you must give location permission",
                                    ""
                                )
                            }
                            else -> {
                                binding.currentLocation.text =
                                    resources.getString(R.string.get_current_location)
                                requireContext().toast("Required Permissions not granted")
                            }
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    // Remember to invoke this method when the custom rationale is closed
                    // or just by default if you don't want to use any custom rationale.
                    token?.continuePermissionRequest()
                }
            })
            .withErrorListener {
                requireContext().toast(it.name)
            }
            .check()
    }


    private fun currentLocation() {

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        fusedLocationClient = FusedLocationProviderClient(requireContext())
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {

                    val geocoder = Geocoder(requireContext())
                    val address = geocoder.getFromLocation(location.latitude, location.longitude, 1)

                    Log.d(TAG, "onCreate: ${address[0].locality} ${address[0].countryName}")
                    val currentAddress = address.getOrNull(0)

                /*    binding.currentLocation.text = "${currentAddress?.locality},${currentAddress?.countryName}"*/


                }


            }
    }


    private fun handleDatabinding(lat: String, lon: String) {

        api.weather("$lat", "$lon","metric", "e13d7e0ca2e481d477ee300f03e94f3d")
            .enqueue(object : Callback<CurrentWeather> {
                override fun onResponse(
                    call: Call<CurrentWeather>,
                    response: Response<CurrentWeather>
                ) {
                    if (response.isSuccessful) {
                        Log.d(TAG, "onResponse: ${response.message()}")
                        val data = response.body()
                        val list = response.body()?.weather?.get(0)
                        binding.apply {

                            if (mSharePrefarence.getTemperatureUnit()==mSharePrefarence.TEMPERATURE_CELSIUS){
                                temperature.text = data?.main?.temp?.toInt().toString()
                            }else{
                                temperature.text=data!!.main!!.temp!!.toDouble().toFahrenheit()
                            }
                            temperatureImage.load("http://openweathermap.org/img/wn/${data?.weather?.getOrNull(0)?.icon?:""}@2x.png")
                            temperatureCondition.text = list?.description
                            lastUpdate.text = "Last Updated :${data?.dt?.toLong()?.toTime().toString()}"
                            sunriseTime.text = "${data?.sys?.sunrise?.toLong()?.toDate().toString()}"
                            sunsetTime.text = "${data?.sys?.sunset?.toLong()?.toDate().toString()}"

                        }

                    } else {
                        Log.d(TAG, "onResponse: ${response.errorBody()}")
                    }
                }

                override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message}")
                }

            })


    }//koi

    private fun recyclerViewHandle(lat: String, lon: String) {

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
                    Log.d(TAG, "onResponse: ${response.message()}")
                    val weatherdata = response.body()
                    val weatherAdapter = weatherdata!!.hourly?.let { AdapterWeather(it) }
                    binding.recyclerview.apply {
                        adapter = weatherAdapter
                        layoutManager =
                            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

                    }

                }
            }

            override fun onFailure(call: Call<ResponseOneCall>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })


    }

    private fun handleBtnClick() {
        binding.next7days.setOnClickListener {
            findNavController().navigate(R.id.sevenDaysWeatherInfo)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
