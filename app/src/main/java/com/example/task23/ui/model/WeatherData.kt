package com.example.task23.ui.model

import com.example.task23.domain.Weather

sealed class WeatherData {

    class Network(val weather: List<Weather>) : WeatherData()

    class Storage(val weather: List<Weather>) : WeatherData()
}