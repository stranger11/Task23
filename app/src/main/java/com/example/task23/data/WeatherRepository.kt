package com.example.task23.data

import android.content.Context
import com.example.task23.ui.Weather

class WeatherRepository(context: Context) {

    private var weatherDao: WeatherDao = WeatherDatabase.getDatabase(context).weatherDao()

    suspend fun getWeatherDatabase() : List<Weather> {
        val weather = weatherDao.readAllData()
        return weather.map { Weather(
            temp = it.temp,
            date = it.date,
            pressure = it.pressure,
            icon = it.icon
        ) }
    }

    suspend fun addWeather(weather: List<WeatherResponse.WeatherData>) {
        val weatherEntity = weather.map { WeatherEntity(
            temp = it.main.temp,
            date = it.dtTxt,
            pressure = it.main.pressure,
            icon = it.weather.first().icon) }
        weatherDao.addWeather(weatherEntity)
    }
}