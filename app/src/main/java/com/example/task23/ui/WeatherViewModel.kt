package com.example.task23.ui

import androidx.lifecycle.*
import com.example.task23.data.WeatherRepositoryImpl
import com.example.task23.ui.model.WeatherType
import com.example.task23.ui.model.WeatherUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherViewModel(repositoryImpl : WeatherRepositoryImpl) : ViewModel() {

    private val _weathers: MutableLiveData<List<WeatherUI>> =
        MutableLiveData<List<WeatherUI>>()
    val weathers: LiveData<List<WeatherUI>> = _weathers
    private val repository = repositoryImpl

    private val _event: MutableLiveData<Event<Unit>> = MutableLiveData<Event<Unit>>()
    val event: LiveData<Event<Unit>> = _event

    init {
        getCurrentData()
    }

    private fun getCurrentData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.weathers()
            when (val weathers = repository.weathers()) {
                is WeatherType.Database -> {
                    withContext(Dispatchers.Main) {
                        _weathers.value = weathers.weather.map {
                            it.toUI()
                        }
                        _event.value = Event(Unit)
                    }
                }
                is WeatherType.Network -> {
                    withContext(Dispatchers.Main) {
                        _weathers.value = weathers.weather.map {
                            it.toUI()
                        }
                    }
                }
            }
        }
    }
}


