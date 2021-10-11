package com.example.task23.data

import com.example.task23.data.database.WeatherDao
import com.example.task23.data.network.WeatherResponse
import com.example.task23.domain.Weather
import com.example.task23.domain.WeatherRepository

class WeatherRepositoryImpl(private val weatherDao: WeatherDao) : WeatherRepository {

    override suspend fun getWeatherDatabase() : List<Weather> = weatherDao.getData().map {
        it.toDomain()
    }

    override suspend fun addWeather(weather: List<WeatherResponse.WeatherData>) {
        weatherDao.insertWeather(weather.map { it.toDatabase() })
    }

    //кеширование - проверка на наличие данных в базе
    override suspend fun weathers() : List<Weather> {
        return if (getWeatherDatabase().isNullOrEmpty()) {
            val weather = getWeather()
            addWeather(weather)
            weather.map { it.toDomain() }
        } else getWeatherDatabase()
    }

    override suspend fun getWeather() : List<WeatherResponse.WeatherData>  {
       return ServiceProvider.getWeatherService()
            .getCurrentWeatherData(CITY, APP_ID, UNITS).list
    }
}






