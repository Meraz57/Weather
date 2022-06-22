package com.example.weather.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.weather.ui.WeatherFragment



class TabLayoutAdapter(fragmentManager: FragmentManager,lifecycler:Lifecycle):FragmentStateAdapter(fragmentManager,lifecycler) {
    private val tabType = arrayListOf(
        "Today",
        "Tomorrow",
    )
    override fun getItemCount(): Int {
        return tabType.size
    }

    override fun createFragment(position: Int): Fragment {


        return WeatherFragment.newInstance(tabType[position])


    }
}