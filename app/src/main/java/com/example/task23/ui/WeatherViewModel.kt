package com.example.task23.ui

import android.util.Log
import androidx.lifecycle.*
import com.example.task23.App.Companion.repository
import com.example.task23.data.ServiceProvider.getWeatherService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val APP_ID = "557dc2784d5b6b18a4c40f345074e4fe"
private const val CITY = "minsk"
private const val UNITS = "metric"

class WeatherViewModel : ViewModel() {

    private var _weathers: MutableLiveData<List<Weather>> =
        MutableLiveData<List<Weather>>()
    var weathers: LiveData<List<Weather>> = _weathers


    init {
        getCurrentData()
        Log.d("LIVEDATE", "работает")
    }

    private fun getCurrentData() {
        viewModelScope.launch(Dispatchers.IO) {
            if (repository.getWeatherDatabase().isNullOrEmpty()) {
                val weather = getWeatherService()
                    .getCurrentWeatherData(CITY, APP_ID, UNITS).list
                repository.addWeather(weather)
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