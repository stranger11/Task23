package com.example.task23.domain

import com.example.task23.data.network.WeatherResponse

interface WeatherRepository {

    suspend fun getWeatherDatabase() : List<Weather>

    suspend fun addWeather(weather: List<WeatherResponse.WeatherData>)

    //кеширование - проверка на наличие данных в базе
    suspend fun weathers() : List<Weather>

    suspend fun getWeather() : List<WeatherResponse.WeatherData>
}
