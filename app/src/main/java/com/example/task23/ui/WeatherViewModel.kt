package com.example.task23.ui

import androidx.lifecycle.*
import com.example.task23.App.Companion.repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private var _weathers: MutableLiveData<List<Weather>> =
        MutableLiveData<List<Weather>>()
    var weathers: LiveData<List<Weather>> = _weathers

    init {
        getCurrentData()
    }

    private fun getCurrentData() {
        viewModelScope.launch(Dispatchers.Main) {
            _weathers.value = repository.weathers()
        }
    }
}