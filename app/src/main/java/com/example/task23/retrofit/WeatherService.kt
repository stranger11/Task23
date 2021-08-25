package com.example.task23.retrofit

import com.example.task23.data.Date
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("data/2.5/weather?")
    fun getCurrentWeatherData(@Query("city name") city: String, @Query("APPID") app_id: String): Call<Date>
}