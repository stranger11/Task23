package com.example.task23

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task23.data.Date
import com.example.task23.databinding.ActivityMainBinding
import com.example.task23.retrofit.WeatherService
import com.example.task23.ui.Adapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BaseUrl = "http://api.openweathermap.org/"
const val AppId = "557dc2784d5b6b18a4c40f345074e4fe"
const val city = "Minsk"

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        getCurrentData()
    }


    private fun getCurrentData() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(WeatherService::class.java)
        val call = service.getCurrentWeatherData(city, AppId)
        call.enqueue(object : Callback<Date> {
            override fun onResponse(call: Call<Date>, response: Response<Date>) {
                if (response.code() == 200) {
                    val weatherResponse = response.body()!!

                    val list = mutableListOf(
                        weatherResponse.dtTxt,
                        weatherResponse.main.temp,
                        weatherResponse.main.pressure,
                        weatherResponse.weather.icon )

                    initRecyclerView()
                    adapter.submitList(list)

                }
            }

            override fun onFailure(call: Call<Date>, t: Throwable) {
               Toast.makeText(applicationContext, "Failed", Toast.LENGTH_LONG).show()
            }
        })
    }



    private fun initRecyclerView() {
        mBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = Adapter()
        mBinding.recyclerView.adapter = adapter


    }



}