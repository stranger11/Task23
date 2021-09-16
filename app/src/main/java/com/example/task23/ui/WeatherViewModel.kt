package com.example.task23.ui

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.task23.data.ServiceProvider.getWeatherService
import com.example.task23.data.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val APP_ID = "557dc2784d5b6b18a4c40f345074e4fe"
private const val CITY = "minsk"
private const val UNITS = "metric"

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    private val okStatusCodes = 200..299
    private var _weathers : MutableLiveData<List<WeatherResponse.WeatherData>> =
        MutableLiveData<List<WeatherResponse.WeatherData>>()
    var weathers: LiveData<List<WeatherResponse.WeatherData>> = _weathers

    init {
        getCurrentData()
        Log.d("LIVEDATE", "работает")
    }

    private fun getCurrentData() {
        getWeatherService()
            .getCurrentWeatherData(CITY, APP_ID, UNITS)
            .enqueue(object : Callback<WeatherResponse> {
                override fun onResponse(
                    call: Call<WeatherResponse>,
                    response: Response<WeatherResponse>
                ) {
                    if (response.code() in okStatusCodes) {
                        val weatherResponse = response.body()!!
                        _weathers.value = weatherResponse.list
                    }
                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    Toast.makeText(getApplication(), t.message, Toast.LENGTH_LONG).show()
                }
            })
    }
}