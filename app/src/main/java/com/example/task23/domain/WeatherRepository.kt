package com.example.task23.domain

import com.example.task23.ui.model.WeatherData


interface WeatherRepository {

    //кеширование - проверка на наличие данных в базе
    suspend fun weathers() : WeatherData
}
