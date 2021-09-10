package com.example.task23.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.task23.data.WeatherResponse

class WeatherFragment : Fragment() {

    var weathers : List<WeatherResponse.WeatherData>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }
}

