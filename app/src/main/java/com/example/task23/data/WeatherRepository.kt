package com.example.task23.data

import android.content.Context
import com.example.task23.data.database.WeatherDao
import com.example.task23.data.database.WeatherDatabase
import com.example.task23.data.database.WeatherEntity
import com.example.task23.data.network.WeatherResponse
import com.example.task23.ui.Weather

class WeatherRepository(private val weatherDao: WeatherDao) {

    suspend fun getWeatherDatabase() : List<Weather> {
        val weather = weatherDao.getData()
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
        weatherDao.insertWeather(weatherEntity)
    }

    suspend fun weather() : List<WeatherResponse.WeatherData>  {
       return ServiceProvider.getWeatherService()
            .getCurrentWeatherData(CITY, APP_ID, UNITS).list
    }
}