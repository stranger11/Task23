package com.example.task23.ui

import com.example.task23.domain.Weather
import com.example.task23.ui.model.WeatherUI

internal fun List<Weather>.toUI() : List<WeatherUI> =
    map { it.toUi()
    }

private fun Weather.toUi() = WeatherUI(
    temp = this.temp,
    pressure = this.pressure,
    date = this.date,
    icon = this.icon
)


