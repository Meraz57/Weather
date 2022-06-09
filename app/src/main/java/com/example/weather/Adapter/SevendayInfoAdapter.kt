package com.example.weather.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.databinding.ItemSevendaysinfoBinding

class SevendayInfoAdapter() : RecyclerView.Adapter<SevendayInfoAdapter.SevendayinfoViewholder>() {

    class SevendayinfoViewholder(val binding: ItemSevendaysinfoBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SevendayinfoViewholder {
        return SevendayinfoViewholder(ItemSevendaysinfoBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: SevendayinfoViewholder, position: Int) {

    }

    override fun getItemCount(): Int {
       return 13
           }


}