package com.example.task23.ui

import com.example.task23.domain.Weather
import com.example.task23.ui.model.WeatherUI

internal fun Weather.toUI() : WeatherUI =
    WeatherUI(
        temp = temp,
        pressure = pressure,
        date = date,
        icon = icon
    )