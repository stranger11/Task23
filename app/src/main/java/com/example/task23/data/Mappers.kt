package com.example.task23.data

import com.example.task23.data.database.WeatherEntity
import com.example.task23.data.network.WeatherResponse
import com.example.task23.domain.Weather


internal fun WeatherEntity.toDomain(): Weather =
    Weather(
        temp = temp,
        date = date,
        pressure = pressure,
        icon = icon
    )

internal fun WeatherResponse.WeatherData.toDatabase(): WeatherEntity =
     WeatherEntity(
        temp = main.temp,
        date = dtTxt,
        pressure = main.pressure,
        icon = weather.first().icon
    )

internal fun WeatherResponse.WeatherData.toDomain(): Weather =
    Weather(
        temp = main.temp,
        date = dtTxt,
        pressure = main.pressure,
        icon = weather.first().icon
    )
