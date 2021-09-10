package com.example.task23

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task23.data.WeatherResponse
import com.example.task23.databinding.ActivityMainBinding
import com.example.task23.retrofit.WeatherService
import com.example.task23.ui.Adapter
import com.example.task23.ui.WeatherFragment
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASEURL = "https://api.openweathermap.org/"
private const val APP_ID = "557dc2784d5b6b18a4c40f345074e4fe"
private const val CITY = "minsk"
private const val UNITS = "metric"
private const val WEATHER_FRAGMENT_TAG = "weather_fragment"

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var weatherFragment: WeatherFragment? = null
    private lateinit var adapter: Adapter
    private val okStatusCodes = 200..299

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initFragment()
        if (weatherFragment?.weathers == null) {
            initRecyclerView()
            getCurrentData()
        } else {
            initRecyclerView()
            adapter.submitList(weatherFragment?.weathers)
        }
    }

    private fun initFragment() {
        weatherFragment = supportFragmentManager
            .findFragmentByTag(WEATHER_FRAGMENT_TAG) as WeatherFragment?
        if (weatherFragment == null) {
            weatherFragment = WeatherFragment()
            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                weatherFragment!!,
                WEATHER_FRAGMENT_TAG
            ).commit()
        }
    }

    private fun getCurrentData() {
        getWeatherService()
            .getCurrentWeatherData(CITY, APP_ID, UNITS)
            .enqueue(object : Callback<WeatherResponse> {
                override fun onResponse(
                    call: Call<WeatherResponse>,
                    response: Response<WeatherResponse>
                ) {
                    if (response.code() in okStatusCodes) {
                        val weatherResponse = response.body()!!
                        weatherFragment?.weathers = weatherResponse.list
                        adapter.submitList(weatherResponse.list)
                    }
                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }
            })
    }

    private fun getWeatherService(): WeatherService {
        val retrofit = Retrofit
            .Builder()
            .client(okHttpClient())
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(WeatherService::class.java)
        return retrofit.create(WeatherService::class.java)
    }

    private fun okHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = Adapter()
        binding.recyclerView.adapter = adapter
    }
}

