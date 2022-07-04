package com.example.weather.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.Debug.getLocation
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.adapter.AdapterWeather
import com.example.weather.R
import com.example.weather.databinding.FragmentSearchByDateBinding
import com.example.weather.network.RetrofitOpenWeatherClient
import com.google.android.material.datepicker.MaterialDatePicker
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import xyz.teamprojectx.weather.data.response.todayForecast.ResponseOneCall


class SearchByDate : Fragment() {
    private var _binding:FragmentSearchByDateBinding?=null
    private val binding get() = _binding!!
    private val api=RetrofitOpenWeatherClient.apiInterfaceOW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       _binding= FragmentSearchByDateBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val drawerLayout=requireActivity().findViewById<DrawerLayout>(R.id.drawerLayout)


        handleSelectDate()
        recyclerViewHandle()



        binding.btnDrawer.setOnClickListener {
            if (drawerLayout.isDrawerOpen(Gravity.LEFT)){
                drawerLayout.closeDrawer(GravityCompat.START)
            }else{
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
                    binding.recycler.apply {
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

    private fun handleSelectDate() {
        val datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select Date").build()
        binding.btnSelectDate.setOnClickListener {
            datePicker.show(requireActivity().supportFragmentManager, "tag")
        }

        datePicker.addOnPositiveButtonClickListener{
            binding.date.text = datePicker.headerText
            Log.d(TAG, "handleSelectDate: $it")
        }
    }



    override fun onDestroy() {
        super.onDestroy()

    }

}