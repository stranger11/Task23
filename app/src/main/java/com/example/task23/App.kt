package com.example.task23

import android.app.Application
import com.example.task23.data.WeatherRepository

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        repository = WeatherRepository(applicationContext)
    }

    companion object {
        lateinit var repository: WeatherRepository
        private set
    }
}