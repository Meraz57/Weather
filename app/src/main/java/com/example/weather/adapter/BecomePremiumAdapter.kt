package com.example.weather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.databinding.ItemSubcriptionPakageBinding

class BecomePremiumAdapter() :
    RecyclerView.Adapter<BecomePremiumAdapter.UpgradePremiumViewholder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpgradePremiumViewholder {
        return UpgradePremiumViewholder(
            ItemSubcriptionPakageBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }


    override fun onBindViewHolder(holder: UpgradePremiumViewholder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 3
    }


    class UpgradePremiumViewholder(val binding: ItemSubcriptionPakageBinding) :
        RecyclerView.ViewHolder(binding.root) {


    }
}