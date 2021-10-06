package com.example.task23.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weathers: List<WeatherEntity>)

    @Query("SELECT * FROM WeatherEntity")
    suspend fun getData(): List<WeatherEntity>
}