package com.example.weather.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weather.Adapter.SevendayInfoAdapter
import com.example.weather.R
import com.example.weather.databinding.FragmentSevenDaysWeatherInfoBinding

class SevenDaysWeatherInfo : Fragment() {

    private var _binding:FragmentSevenDaysWeatherInfoBinding?=null
    private val binding get() = _binding!!
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

    }

    private fun handleRecyclerview(){
        val adapter= SevendayInfoAdapter()
        binding.sevendaysrecyclerview.adapter=adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}