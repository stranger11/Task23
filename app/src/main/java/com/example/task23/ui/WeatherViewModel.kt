package com.example.task23.ui

import androidx.lifecycle.*
import com.example.task23.data.WeatherRepositoryImpl
import com.example.task23.ui.model.WeatherUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel(repositoryImpl : WeatherRepositoryImpl) : ViewModel() {

    private var _weathers: MutableLiveData<List<WeatherUI>> =
        MutableLiveData<List<WeatherUI>>()
    var weathers: LiveData<List<WeatherUI>> = _weathers
    private var repository = repositoryImpl

    init {
        getCurrentData()
    }

    private fun getCurrentData() {
        viewModelScope.launch(Dispatchers.Main) {
            _weathers.value = repository.weathers().map{
                it.toUI()
            }
        }
    }
}


