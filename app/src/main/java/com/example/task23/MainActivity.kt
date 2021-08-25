package com.example.task23

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task23.data.WeatherResponse
import com.example.task23.databinding.ActivityMainBinding
import com.example.task23.retrofit.WeatherService
import com.example.task23.ui.Adapter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASEURL = "https://api.openweathermap.org/"
const val APPID = "557dc2784d5b6b18a4c40f345074e4fe"
const val CITY = "minsk"

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initRecyclerView()
        getCurrentData()
    }

    private fun getCurrentData() {
        val retrofit = Retrofit
            .Builder()
            .client(okkhttpClient())
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(WeatherService::class.java)
        val call = service.getCurrentWeatherData(CITY, APPID)
        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.code() == 200) {
                    val weatherResponse = response.body()!!
                    adapter.submitList(weatherResponse.list)
                }
            }
            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Toast.makeText(applicationContext, R.string.failed, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun okkhttpClient() : OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
    }
    private fun initRecyclerView() {
        mBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = Adapter()
        mBinding.recyclerView.adapter = adapter
    }
}