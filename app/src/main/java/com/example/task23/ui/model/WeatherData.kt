package com.example.task23.ui.model

import com.example.task23.domain.Weather

sealed class WeatherData {

    class WeatherNetwork(val weather: List<Weather>) : WeatherData()

    class WeatherDatabase(val weather: List<Weather>) : WeatherData()
}