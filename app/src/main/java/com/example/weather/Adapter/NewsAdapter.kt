package com.example.weather.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.databinding.ItemNewsBinding

class NewsAdapter() : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private lateinit var mlistener:onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun onItemClickListener(listener:onItemClickListener){
    mlistener=listener

    }

    class NewsViewHolder(val binding: ItemNewsBinding,listener: onItemClickListener) : RecyclerView.ViewHolder(binding.root) {

        init {
        itemView.setOnClickListener {
            listener.onItemClick(adapterPosition)
        }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {

        return NewsViewHolder(
            ItemNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),mlistener
        )

    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {


    }


    override fun getItemCount(): Int {
        return 10
    }

}