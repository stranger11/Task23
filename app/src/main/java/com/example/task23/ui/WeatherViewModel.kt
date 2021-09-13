package com.example.task23.ui

import androidx.lifecycle.ViewModel
import com.example.task23.data.WeatherResponse

class WeatherViewModel : ViewModel() {

    var weathers: List<WeatherResponse.WeatherData>? = null
}