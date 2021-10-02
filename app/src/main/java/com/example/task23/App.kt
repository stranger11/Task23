package com.example.task23

import android.app.Application
import com.example.task23.data.WeatherRepository
import com.example.task23.data.database.WeatherDao
import com.example.task23.data.database.WeatherDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        val weatherDao: WeatherDao = WeatherDatabase.getDatabase(applicationContext).weatherDao()
        repository = WeatherRepository(weatherDao)
    }

    companion object {
        lateinit var repository: WeatherRepository
        private set
    }
}