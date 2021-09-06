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

private const val MY_TEMP = 15

class Adapter : ListAdapter<WeatherResponse.WeatherData, RecyclerView.ViewHolder>(ContactItemDiffCallback) {

    companion object {
        const val VIEW_COLD_WEATHER = 1
        const val VIEW_HOT_WEATHER = 2
    }

    override fun getItemViewType(position: Int): Int {
        return if(getItem(position).main.temp < MY_TEMP) {
            VIEW_COLD_WEATHER
        } else {
            VIEW_HOT_WEATHER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_COLD_WEATHER) {
            ViewHolderHot(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.recycler_view_item_layout,
                    parent,
                    false)
            )
        } else {
            ViewHolderCold(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.recycler_view_item_layout2,
                    parent,
                    false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_COLD_WEATHER) {
            (holder as ViewHolderHot).bind(getItem(position))
        } else {
            (holder as ViewHolderCold).bind(getItem(position))
        }
    }
}

class ViewHolderCold(view: View) : RecyclerView.ViewHolder(view) {
    private var temperature: TextView = view.findViewById(R.id.temp2)
    private var pressure: TextView = view.findViewById(R.id.pressure2)
    private var date: TextView = view.findViewById(R.id.date2)
    private var icon: ImageView = view.findViewById(R.id.icon2)

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

class ViewHolderHot(view: View) : RecyclerView.ViewHolder(view) {
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