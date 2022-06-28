package com.example.weather.adapter

import android.annotation.SuppressLint
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup


import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.databinding.ItemWeatherBinding
import com.example.weather.ui.AllFuction
import com.example.weather.dataclass.data.todayForecast.Hourly

class AdapterWeather(val list: List<Hourly?>) : RecyclerView.Adapter<AdapterWeather.WeatherViewholder>() {
   private var selectedposition=0

    class WeatherViewholder(val binding: ItemWeatherBinding) :
        RecyclerView.ViewHolder(binding.root) {



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewholder {
        return WeatherViewholder(
            ItemWeatherBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    @SuppressLint("NotifyDataSetChanged", "ResourceAsColor")
    override fun onBindViewHolder(holder: WeatherViewholder, position: Int) {
        if (selectedposition==holder.adapterPosition){
        holder.binding.layout.background= ColorDrawable(R.color.recyclerclr)
        }else{
            holder.binding.layout.background=ColorDrawable(R.color.black)
        }

        holder.itemView.setOnClickListener {
            selectedposition=holder.adapterPosition
             notifyDataSetChanged()
        }


        //Bind Data
        val item= list[position]?.weather?.get(0)
        val item1=list[position]
        holder.binding.apply {
            weatherFormat.text=item?.description
            time.text= item1?.dt?.let { AllFuction.convertLongToTime(it.toLong()) }
        }



    }

    override fun getItemCount(): Int {
        return list.size
    }



}


