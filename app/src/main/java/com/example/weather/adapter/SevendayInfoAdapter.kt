package com.example.weather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.databinding.ItemSevendaysinfoBinding
import com.example.weather.dataclass.data.todayForecast.Hourly
import xyz.teamprojectx.weather.data.response.nextWeek.Main

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
                temmMax.text="+${item.tempMax.toString()}°"
            }
            if (item != null) {
                tempMin.text="+${item.tempMin.toString()}°"
            }

        }

    }

    override fun getItemCount(): Int {
       return 13
           }


}