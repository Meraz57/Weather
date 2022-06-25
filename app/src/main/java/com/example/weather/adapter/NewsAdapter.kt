package com.example.weather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.weather.databinding.ItemNewsBinding
import com.example.weather.dataclass.data.Data

class NewsAdapter(val list: List<Data?>?) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>()    {
    private lateinit var mlistener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun onItemClickListener(listener: onItemClickListener) {
        mlistener = listener

    }

    class NewsViewHolder(val binding: ItemNewsBinding, listener: onItemClickListener) :
        RecyclerView.ViewHolder(binding.root) {

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
            ), mlistener
        )

    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = list?.get(position)
        holder.binding.apply {
            title.text = item?.title
            image.load(item?.image)
        }

    }


    override fun getItemCount(): Int {
        return list!!.size
    }

}