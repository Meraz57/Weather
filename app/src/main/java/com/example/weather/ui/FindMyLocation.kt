package com.example.weather.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weather.Adapter.AdapterWeather
import com.example.weather.R
import com.example.weather.databinding.FragmentFindMyLocationBinding


class FindMyLocation : Fragment() {


    private var _binding: FragmentFindMyLocationBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFindMyLocationBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleRecyclerview()

    }

    fun handleRecyclerview(){
        val weatherAdapter=AdapterWeather()
        binding.recyclerview.adapter=weatherAdapter

    }


        override fun onDestroy() {
            super.onDestroy()
            _binding = null
        }
    }