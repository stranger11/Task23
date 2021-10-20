package com.example.task23.data

import com.example.task23.data.database.WeatherDao
import com.example.task23.data.network.WeatherResponse
import com.example.task23.domain.Weather
import com.example.task23.domain.WeatherRepository
import com.example.task23.ui.model.WeatherType
import com.example.task23.ui.toUI
import java.lang.Exception

class WeatherRepositoryImpl(private val weatherDao: WeatherDao) : WeatherRepository {

    private suspend fun getWeatherDatabase() : WeatherType =
        WeatherType.Database(weatherDao.getData().map {
        it.toDomain()
    })

    private suspend fun updateDatabase(weather: List<WeatherResponse.WeatherData>) {
        weatherDao.updateWeather(weather.map { it.toDatabase() })
    }

    //кеширование - проверка на наличие данных в базе
    override suspend fun weathers() : WeatherType {
        return try {
            val weather = loadWeather()
            updateDatabase(weather)
            WeatherType.Network(weather.map { it.toDomain() })
        } catch (e : Exception) {
            getWeatherDatabase()
        }
    }

    private suspend fun loadWeather() : List<WeatherResponse.WeatherData>  {
       return ServiceProvider.getWeatherService()
            .getCurrentWeatherData(CITY, APP_ID, UNITS).list
    }
}






