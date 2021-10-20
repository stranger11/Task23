package com.example.task23.ui

import com.example.task23.domain.Weather
import com.example.task23.ui.model.WeatherUI

internal fun List<Weather>.toUI() : List<WeatherUI> =
    map {
        WeatherUI(
            temp = it.temp,
            pressure = it.pressure,
            date = it.date,
            icon = it.icon
        )
    }

//    WeatherUI(
//        temp = temp,
//        pressure = pressure,
//        date = date,
//        icon = icon
//    )