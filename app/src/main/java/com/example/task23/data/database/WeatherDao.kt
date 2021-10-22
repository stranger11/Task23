package com.example.task23.data.database

import androidx.room.*

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weathers: List<WeatherEntity>)

    @Query("SELECT * FROM WeatherEntity")
    suspend fun getData(): List<WeatherEntity>

    @Query("DELETE FROM WeatherEntity")
    suspend fun deleteWeather()

    @Transaction
    suspend fun updateWeather(weathers: List<WeatherEntity>) {
        deleteWeather()
        insertWeather(weathers)
    }
}