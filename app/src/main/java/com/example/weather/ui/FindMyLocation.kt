package com.example.weather.ui

import android.Manifest
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.adapter.AdapterWeather
import com.example.weather.databinding.FragmentFindMyLocationBinding
import com.example.weather.dataclass.data.currentweather.CurrentWeather
import com.example.weather.dataclass.data.viewModel.ExploreViewModel
import com.example.weather.factory.ExploreFactory
import com.example.weather.network.RetrofitOpenWeatherClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import xyz.teamprojectx.weather.data.repository.ExploreRepository
import com.example.weather.dataclass.data.Address
import xyz.teamprojectx.weather.data.response.todayForecast.ResponseOneCall


class FindMyLocation : Fragment() {


    private var _binding: FragmentFindMyLocationBinding? = null
    private val binding get() = _binding!!
    private val api = RetrofitOpenWeatherClient.apiInterfaceOW

    private val viewModel by activityViewModels<ExploreViewModel> {
        ExploreFactory(ExploreRepository(requireContext()))
    }
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

        recyclerViewHandle()
        handleBtnClick()
        handleWeather()
        handleDatabinding()
        checkPermission()


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

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

    private fun getNextWeek(location: Location) {

        viewModel.nextWeek(location.latitude.toString(), location.longitude.toString())
    }

    private fun getWeather(location: Location) {

        viewModel.weather(location.latitude.toString(), location.longitude.toString())

    }

    private fun getTodayForecast(location: Location) {

        viewModel.todayForecast(location.latitude.toString(), location.longitude.toString())

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
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {

                    getWeather(location)
                    getTodayForecast(location)
                    getNextWeek(location)
                    val geocoder = Geocoder(requireContext())
                    val address = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    Log.d(TAG, "onCreate: ${address[0].locality} ${address[0].countryName} ")
                    val currentAddress = address.getOrNull(0)
                    if (currentAddress != null) {
                        viewModel.address.value = Address(
                            currentAddress.latitude.toString(),
                            currentAddress.longitude.toString(),
                            currentAddress.locality,
                            currentAddress.countryName
                        )
                        binding.currentLocation.text =
                            "${currentAddress.locality}, ${currentAddress.countryName}"

                    }


                }
            }

    }


    private fun handleWeather() {
        api.weather("23", "90", "e13d7e0ca2e481d477ee300f03e94f3d")
            .enqueue(object : Callback<CurrentWeather> {
                override fun onResponse(
                    call: Call<CurrentWeather>,
                    response: Response<CurrentWeather>
                ) {
                    if (response.isSuccessful) {
                        Log.d(TAG, "onResponse: ")
                    }
                }

                override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message}")
                }

            })
    }


    private fun handleDatabinding() {

        api.weather("23", "90", "e13d7e0ca2e481d477ee300f03e94f3d")
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

                            temperature.text = data?.main?.temp.toString()
                            temperatureCondition.text = list?.description
                            address.text = data?.name
                            lastUpdate.text = "Last Updated :${data?.timezone}"
                            sunriseTime.text = "${data?.sys?.sunrise}"
                            sunsetTime.text = "${data?.sys?.sunset}"

                        }

                    } else {
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

