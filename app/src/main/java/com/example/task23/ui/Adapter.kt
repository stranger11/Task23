package com.example.task23.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task23.R
import com.example.task23.data.WeatherResponse


class Adapter : ListAdapter<WeatherResponse.WeatherData, ViewHolder>(ContactItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_view_item_layout,
            parent,
            false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private var temperature: TextView = view.findViewById(R.id.temp)
    private var pressure: TextView = view.findViewById(R.id.pressure)
    private var date: TextView = view.findViewById(R.id.date)
    private var icon: ImageView = view.findViewById(R.id.icon)

    fun bind(item: WeatherResponse.WeatherData) {
        temperature.text = item.main.temp.toString()
        pressure.text = item.main.pressure.toString()
        date.text = item.dtTxt
        val weatherIcon = item.weather.first().icon
        Glide.with(icon.context)
            .load(getUrl(weatherIcon))
            .into(icon)
    }

    private fun getUrl(iconCode: String) : String {
        return "https://openweathermap.org/img/wn/$iconCode@2x.png"
    }
}

object ContactItemDiffCallback : DiffUtil.ItemCallback<WeatherResponse.WeatherData>() {
    override fun areItemsTheSame(oldItem: WeatherResponse.WeatherData,
                                 newItem: WeatherResponse.WeatherData)
            : Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: WeatherResponse.WeatherData,
                                    newItem: WeatherResponse.WeatherData)
            : Boolean = oldItem == newItem
}