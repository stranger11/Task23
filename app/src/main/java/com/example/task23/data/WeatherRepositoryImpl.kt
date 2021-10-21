package com.example.task23.data

import com.example.task23.data.database.WeatherDao
import com.example.task23.data.network.WeatherResponse
import com.example.task23.domain.WeatherRepository
import com.example.task23.ui.model.WeatherData
import java.lang.Exception

class WeatherRepositoryImpl(private val weatherDao: WeatherDao) : WeatherRepository {

    private suspend fun getWeatherDatabase() : WeatherData =
        WeatherData.Storage(weatherDao.getData().map {
        it.toDomain()
    })

    private suspend fun updateDatabase(weather: List<WeatherResponse.WeatherData>) {
        weatherDao.updateWeather(weather.map { it.toDatabase() })
    }

    override suspend fun weathers() : WeatherData {
        return try {
            val weather = loadWeather()
            updateDatabase(weather)
            WeatherData.Network(weather.map { it.toDomain() })
        } catch (e : Exception) {
            getWeatherDatabase()
        }
    }

    private suspend fun loadWeather() : List<WeatherResponse.WeatherData>  {
       return ServiceProvider.getWeatherService()
            .getCurrentWeatherData(CITY, APP_ID, UNITS).list
    }
}






