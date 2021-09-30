package com.example.task23.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWeather(weather: List<WeatherEntity>)

    @Query("SELECT * FROM WeatherEntity")
    suspend fun readAllData(): List<WeatherEntity>
}