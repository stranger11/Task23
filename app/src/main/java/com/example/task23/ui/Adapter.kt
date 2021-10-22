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
import com.example.task23.data.CITY_NAME
import com.example.task23.ui.model.WeatherUI

private const val MY_TEMP = 5

class Adapter(private val onClick: (String, String, String) -> Unit)
    : ListAdapter<WeatherUI,
        RecyclerView.ViewHolder>(ContactItemDiffCallback) {

    companion object {
        const val VIEW_COLD_WEATHER = 1
        const val VIEW_HOT_WEATHER = 2
    }

    override fun getItemViewType(position: Int): Int {
        return if(getItem(position).temp < MY_TEMP) {
            VIEW_COLD_WEATHER
        } else {
            VIEW_HOT_WEATHER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_COLD_WEATHER) {
            ViewHolderHot(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.hot_weather_item_layout,
                    parent,
                    false)
            )
        } else {
            ViewHolderCold(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.cold_weather_item_layout,
                    parent,
                    false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_COLD_WEATHER) {
            (holder as ViewHolderHot).bind(getItem(position), onClick)
        } else {
            (holder as ViewHolderCold).bind(getItem(position), onClick)
        }
    }
}

class ViewHolderCold(view: View) : RecyclerView.ViewHolder(view) {
    private var temperature: TextView = view.findViewById(R.id.temp_cold)
    private var pressure: TextView = view.findViewById(R.id.pressure_cold)
    private var date: TextView = view.findViewById(R.id.date_cold)
    private var icon: ImageView = view.findViewById(R.id.icon_cold)

    fun bind(item: WeatherUI, onClick: (String, String, String) -> Unit) {
        temperature.text = temperature.context.getString(R.string.degree_celsius, item.temp)
        pressure.text = item.pressure.toString()
        date.text = item.date
        val weatherIcon = item.icon
        Glide.with(icon.context)
            .load(getUrl(weatherIcon))
            .into(icon)
        itemView.setOnLongClickListener {
            onClick(
                CITY_NAME,
                item.date,
                temperature.context.getString(R.string.degree_celsius, item.temp))
            return@setOnLongClickListener true
        }

    }

    private fun getUrl(iconCode: String) : String {
        return "https://openweathermap.org/img/wn/$iconCode@2x.png"
    }
}

class ViewHolderHot(view: View) : RecyclerView.ViewHolder(view) {
    private var temperature: TextView = view.findViewById(R.id.temp_hot)
    private var pressure: TextView = view.findViewById(R.id.pressure_hot)
    private var date: TextView = view.findViewById(R.id.date_hot)
    private var icon: ImageView = view.findViewById(R.id.icon_hot)

    fun bind(item: WeatherUI, onClick: (String, String, String) -> Unit) {
        temperature.text = temperature.context.getString(R.string.degree_celsius, item.temp)
        pressure.text = item.pressure.toString()
        date.text = item.date
        val weatherIcon = item.icon
        Glide.with(icon.context)
            .load(getUrl(weatherIcon))
            .into(icon)
        itemView.setOnLongClickListener {
            onClick(
                CITY_NAME,
                item.date,
                temperature.context.getString(R.string.degree_celsius, item.temp))
            return@setOnLongClickListener true
        }
    }

    private fun getUrl(iconCode: String) : String {
        return "https://openweathermap.org/img/wn/$iconCode@2x.png"
    }
}

object ContactItemDiffCallback : DiffUtil.ItemCallback<WeatherUI>() {
    override fun areItemsTheSame(oldItem: WeatherUI,
                                 newItem: WeatherUI
    )
            : Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: WeatherUI,
                                    newItem: WeatherUI
    )
            : Boolean = oldItem == newItem
}