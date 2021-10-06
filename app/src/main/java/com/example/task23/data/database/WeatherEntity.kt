package com.example.task23.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "weatherEntity")
data class WeatherEntity (

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val temp: Double,
    val date: String,
    val pressure: Int,
    val icon: String
    )
