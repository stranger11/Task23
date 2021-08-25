package com.example.task23.retrofit


import com.example.task23.data.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("data/2.5/forecast")
    fun getCurrentWeatherData(@Query("q") city: String, @Query("appid") app_id: String): Call<WeatherResponse>
}