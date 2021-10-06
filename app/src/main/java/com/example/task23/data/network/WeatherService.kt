package com.example.task23.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("data/2.5/forecast")
    suspend fun getCurrentWeatherData(
        @Query("q") city: String,
        @Query("appid") app_id: String,
        @Query("units") units: String): WeatherResponse
}