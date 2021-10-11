package com.example.task23

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.task23.data.WeatherRepositoryImpl
import com.example.task23.data.database.WeatherDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        val weatherDao = getDatabase(applicationContext).weatherDao()
        repositoryImpl = WeatherRepositoryImpl(weatherDao)
    }

    companion object {
        lateinit var repositoryImpl: WeatherRepositoryImpl
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