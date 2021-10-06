package com.example.task23.data

import com.example.task23.App.Companion.weatherDao
import com.example.task23.data.database.WeatherEntity
import com.example.task23.data.network.WeatherResponse
import com.example.task23.ui.Weather

class WeatherRepository {

    private suspend fun getWeatherDatabase() : List<Weather> {
        val weather = weatherDao.getData()
        return weather.map { Weather(
            temp = it.temp,
            date = it.date,
            pressure = it.pressure,
            icon = it.icon
        ) }
    }

    private suspend fun addWeather(weather: List<WeatherResponse.WeatherData>) {
        val weatherEntity = weather.map { WeatherEntity(
            temp = it.main.temp,
            date = it.dtTxt,
            pressure = it.main.pressure,
            icon = it.weather.first().icon) }
        weatherDao.insertWeather(weatherEntity)
    }

    //кеширование - проверка на наличие данных в базе
    suspend fun weathers() : List<Weather> {
        return if (getWeatherDatabase().isNullOrEmpty()) {
            val weather = getWeather()
            addWeather(weather)
            val weathers = weather.map {
                Weather(
                    temp = it.main.temp,
                    date = it.dtTxt,
                    pressure = it.main.pressure,
                    icon = it.weather.first().icon
                )
            }
            weathers
        } else getWeatherDatabase()
    }

    private suspend fun getWeather() : List<WeatherResponse.WeatherData>  {
       return ServiceProvider.getWeatherService()
            .getCurrentWeatherData(CITY, APP_ID, UNITS).list
    }
}