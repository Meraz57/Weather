package com.example.weather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.databinding.ItemSevendaysinfoBinding
import com.example.weather.dataclass.data.nextWeek.Main
import com.example.weather.ui.mSharePrefarence
import com.example.weather.ui.toFahrenheit

class SevendayInfoAdapter(val list: Main?) : RecyclerView.Adapter<SevendayInfoAdapter.SevendayinfoViewholder>() {

    class SevendayinfoViewholder(val binding: ItemSevendaysinfoBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SevendayinfoViewholder {
        return SevendayinfoViewholder(ItemSevendaysinfoBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: SevendayinfoViewholder, position: Int) {
        val item=list
        holder.binding.apply {
            if (item != null) {
                if (mSharePrefarence.getTemperatureUnit()==mSharePrefarence.TEMPERATURE_CELSIUS){
                    temmMax.text="+${item.tempMax?.toInt().toString()}°"
                }else{
                    temmMax.text="${item.tempMax?.toDouble()?.toFahrenheit()}°"
                }

            }

            if (item != null) {
                if (mSharePrefarence.getTemperatureUnit()==mSharePrefarence.TEMPERATURE_CELSIUS){
                    tempMin.text="+${item.tempMin?.toInt().toString()}°"
                }else{
                    tempMin.text="+${item.tempMin?.toDouble()?.toFahrenheit()}°"
                }


            }

        }

    }

    override fun getItemCount(): Int {
        return 7
           }


}