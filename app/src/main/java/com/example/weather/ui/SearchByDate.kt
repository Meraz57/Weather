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
import com.example.weather.adapter.AdapterWeather
import com.example.weather.R
import com.example.weather.databinding.FragmentSearchByDateBinding
import com.google.android.material.datepicker.MaterialDatePicker


class SearchByDate : Fragment() {
    private var _binding:FragmentSearchByDateBinding?=null
    private val binding get() = _binding!!
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
        handleWeatherAdapter()
        binding.btnDrawer.setOnClickListener {
            if (drawerLayout.isDrawerOpen(Gravity.LEFT)){
                drawerLayout.closeDrawer(GravityCompat.START)
            }else{
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }

    }




    private fun handleWeatherAdapter(){
        val adapterWeather=AdapterWeather()
        binding.searchbyDateRecycler.adapter=adapterWeather
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