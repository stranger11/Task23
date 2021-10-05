package com.example.task23.ui

import androidx.lifecycle.*
import com.example.task23.App.Companion.repository
import com.example.task23.data.APP_ID
import com.example.task23.data.CITY
import com.example.task23.data.ServiceProvider.getWeatherService
import com.example.task23.data.UNITS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherViewModel : ViewModel() {

    private var _weathers: MutableLiveData<List<Weather>> =
        MutableLiveData<List<Weather>>()
    var weathers: LiveData<List<Weather>> = _weathers

    init {
        getCurrentData()
    }

    private fun getCurrentData() {
        viewModelScope.launch(Dispatchers.IO) {
            if (repository.getWeatherDatabase().isNullOrEmpty()) {
                val weather = repository.weather()
                repository.addWeather(repository.weather())
                withContext(Dispatchers.Main) {
                    _weathers.value = weather.map {
                        Weather(
                            temp = it.main.temp,
                            date = it.dtTxt,
                            pressure = it.main.pressure,
                            icon = it.weather.first().icon
                        )
                    }
                }
            } else {
                val weather = repository.getWeatherDatabase()
                withContext(Dispatchers.Main) {
                    _weathers.value = weather
                }
            }
        }
    }
}