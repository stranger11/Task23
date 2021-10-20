package com.example.task23.ui.model

import com.example.task23.domain.Weather

sealed class WeatherType {

    class Network(val weather: List<Weather>) : WeatherType()

    class Database(val weather: List<Weather>) : WeatherType()
}