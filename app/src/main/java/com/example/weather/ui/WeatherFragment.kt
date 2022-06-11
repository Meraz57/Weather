package com.example.weather.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weather.R
import com.example.weather.databinding.FragmentWeatherBinding
import com.example.weather.databinding.FragmentWeatherFormatBinding

class WeatherFragment : Fragment() {
    private var param1: String? = null
    private var _binding :FragmentWeatherBinding?=null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    _binding= FragmentWeatherBinding.inflate(inflater,container,false   )
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            WeatherFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }

        private const val TAG = "WeatherFragment"
        private const val ARG_PARAM1 = "param1"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





    }

    override fun onResume() {
        super.onResume()



            }

}