package com.example.task23.ui

import androidx.lifecycle.*
import com.example.task23.data.WeatherRepositoryImpl
import com.example.task23.ui.model.WeatherData
import com.example.task23.ui.model.WeatherUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherViewModel(repositoryImpl : WeatherRepositoryImpl) : ViewModel() {

    private val _weathers: MutableLiveData<List<WeatherUI>> =
        MutableLiveData<List<WeatherUI>>()
    val weathers: LiveData<List<WeatherUI>> = _weathers
    private val repository = repositoryImpl
    private val _eventErrorNetwork: MutableLiveData<Event<Unit>> = MutableLiveData<Event<Unit>>()
    val eventErrorNetwork: LiveData<Event<Unit>> = _eventErrorNetwork

    init {
        getCurrentData()
    }

    private fun getCurrentData() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val getWeather = repository.weathers()) {
                is WeatherData.Storage -> {
                    withContext(Dispatchers.Main) {
                        _weathers.value = getWeather.weather.toUI()
                        _eventErrorNetwork.value = Event(Unit)
                    }
                }
                is WeatherData.Network -> {
                    withContext(Dispatchers.Main) {
                        _weathers.value = getWeather.weather.toUI()
                    }
                }
            }
        }
    }
}


