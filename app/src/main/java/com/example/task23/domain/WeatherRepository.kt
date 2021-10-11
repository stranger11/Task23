package com.example.task23.domain

import com.example.task23.data.network.WeatherResponse

interface WeatherRepository {

    //кеширование - проверка на наличие данных в базе
    suspend fun weathers() : List<Weather>
}
