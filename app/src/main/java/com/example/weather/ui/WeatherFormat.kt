package com.example.weather.ui
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.weather.R
import com.example.weather.databinding.FragmentWeatherFormatBinding
class WeatherFormat : Fragment() {
    private var _binding: FragmentWeatherFormatBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWeatherFormatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mSharePrefarence.init(requireContext())
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }


        onCheckedChanged()
        defultSetting()



    }

    private fun onCheckedChanged() {


        binding.rgTemperature.setOnCheckedChangeListener { radioGroup, id ->
            if (id == R.id.radioDegree) {
                mSharePrefarence.setTemperatureUnit(mSharePrefarence.TEMPERATURE_CELSIUS)
            }else{
                mSharePrefarence.setTemperatureUnit(mSharePrefarence.TEMPERATURE_FAHRENHEIT)
            }
        }
        binding.rgWindSpeed.setOnCheckedChangeListener { _, id ->

            when (id) {
                R.id.radioMs -> {
                    mSharePrefarence.setWindFormat(mSharePrefarence.WIND_MS)
                }
                R.id.radioKmh -> {
                    mSharePrefarence.setWindFormat(mSharePrefarence.WIND_KMH)
                }
                R.id.radioMph -> {
                    mSharePrefarence.setWindFormat(mSharePrefarence.WIND_MPH)
                }
            }
        }
        binding.rgTimeFormat.setOnCheckedChangeListener { _, id ->
            if (id == R.id.radio24hr) {
                mSharePrefarence.setTimeFormat(mSharePrefarence.TIME_24)
            } else if (id == R.id.radio12hr) {
                mSharePrefarence.setTimeFormat(mSharePrefarence.TIME_12)
            }
        }
    }

    private fun defultSetting() {
        binding.apply {
            if (mSharePrefarence.getTemperatureUnit() == mSharePrefarence.TEMPERATURE_CELSIUS) {
                rgTemperature.check(R.id.radioDegree)
            } else {
                rgTemperature.check(R.id.radioFarenheit)
            }

            binding.apply {
                if (mSharePrefarence.getWindFormat() == mSharePrefarence.WIND_MS) {
                    rgTimeFormat.check(R.id.windSpeed)
                } else {
                    rgTimeFormat.check(R.id.radioMph)
                }

            }

            when (mSharePrefarence.getWindFormat()) {
                mSharePrefarence.WIND_MS -> {
                    rgWindSpeed.check(R.id.radioMs)
                }
                mSharePrefarence.WIND_KMH -> {
                    rgWindSpeed.check(R.id.radioKmh)
                }
                mSharePrefarence.WIND_MPH -> {
                    rgWindSpeed.check(R.id.radioMph)
                }
            }

            if (mSharePrefarence.getTimeFormat() == mSharePrefarence.TIME_24) {
                rgTimeFormat.check(R.id.radio24hr)
            } else {
                rgTimeFormat.check(R.id.radio12hr)
            }

        }
    }


}