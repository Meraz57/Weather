package com.example.weather.adapter

import android.annotation.SuppressLint
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup


import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.weather.R
import com.example.weather.databinding.ItemWeatherBinding
import com.example.weather.ui.AllFuction
import com.example.weather.dataclass.data.todayForecast.Hourly
import com.example.weather.ui.toTime
import java.util.*

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
        val item=list[position]
        val imageUrl = item!!.weather?.getOrNull(0)?.icon?:""
        holder.binding.apply {
            weatherFormat.text = capitalization(item.weather?.getOrNull(0)?.description?:"Unknown")
            image.load("http://openweathermap.org/img/wn/$imageUrl@2x.png")
            time.text=item.dt?.toLong()?.toTime().toString()


        }



    }

    private fun capitalization(title:String):String{
        var result = ""
        title.splitToSequence(" ").filter { it.isNotEmpty() }.toList().forEach { data ->
            result += data.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }+" "
        }
        return result
    }

    override fun getItemCount(): Int {
        return list.size
    }



}


