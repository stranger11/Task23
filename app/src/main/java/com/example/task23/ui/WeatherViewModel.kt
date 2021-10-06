package com.example.task23.ui

import androidx.lifecycle.*
import com.example.task23.data.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel(repository : WeatherRepository) : ViewModel() {

    private var _weathers: MutableLiveData<List<Weather>> =
        MutableLiveData<List<Weather>>()
    var weathers: LiveData<List<Weather>> = _weathers
    private var repository = repository

    init {
        getCurrentData()
    }

    private fun getCurrentData() {
        viewModelScope.launch(Dispatchers.Main) {
            _weathers.value = repository.weathers()
        }
    }
}