package com.example.task23.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.task23.data.WeatherRepositoryImpl

class WeatherViewModelFactory(private val repositoryImpl: WeatherRepositoryImpl) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)){
            return WeatherViewModel(repositoryImpl) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}