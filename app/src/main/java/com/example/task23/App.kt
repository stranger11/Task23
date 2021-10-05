package com.example.task23

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.task23.data.WeatherRepository
import com.example.task23.data.database.WeatherDao
import com.example.task23.data.database.WeatherDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        val weatherDao: WeatherDao = getDatabase(applicationContext).weatherDao()
        repository = WeatherRepository(weatherDao)
    }

    companion object {
        lateinit var repository: WeatherRepository
        private set
        private fun getDatabase(context: Context): WeatherDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                WeatherDatabase::class.java,
                "task_database"
            ).build()
        }
    }
}