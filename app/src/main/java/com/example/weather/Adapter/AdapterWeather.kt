package com.example.weather.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView

import androidx.recyclerview.widget.RecyclerView
import com.example.weather.databinding.ItemWeatherBinding

class AdapterWeather() : RecyclerView.Adapter<AdapterWeather.WeatherViewholder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewholder {
        return WeatherViewholder(
            ItemWeatherBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: WeatherViewholder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 10
    }

    class WeatherViewholder(val binding: ItemWeatherBinding) :
        RecyclerView.ViewHolder(binding.root) {


    }

}


