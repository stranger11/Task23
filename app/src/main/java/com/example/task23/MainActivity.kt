package com.example.task23

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task23.data.WeatherResponse
import com.example.task23.databinding.ActivityMainBinding
import com.example.task23.retrofit.WeatherService
import com.example.task23.ui.Adapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
private const val JSON_WEATHER_KEY = "JSON WEATHER KEY"

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: Adapter
    private lateinit var weatherFromJson: List<WeatherResponse.WeatherData>
    private lateinit var weatherInString: String
    private val okStatusCodes = 200..299

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        getCurrentData()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(JSON_WEATHER_KEY, weatherInString)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        weatherInString = savedInstanceState.getString(JSON_WEATHER_KEY).toString()
        getCurrentDataFromJson(weatherInString)
    }

    private fun getCurrentData() {
        getWeatherService()
            .getCurrentWeatherData(CITY, APP_ID, UNITS)
            .enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>) {
                for (value in okStatusCodes) {
                    if (response.code() == value) {
                        val weatherResponse = response.body()!!
                        adapter.submitList(weatherResponse.list)
                        val json = Gson()
                        weatherInString = json.toJson(weatherResponse.list)
                    }
                }
            }
            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getCurrentDataFromJson(weatherInJson : String) {
        val json = Gson()
        val tokenForParse = object : TypeToken<MutableList<WeatherResponse.WeatherData>>() {}.type
        weatherFromJson = json.fromJson(weatherInJson, tokenForParse)
        adapter.submitList(weatherFromJson)
        Toast.makeText(this, "получили из jsona", Toast.LENGTH_LONG).show()
    }

    private fun getWeatherService() : WeatherService {
        val retrofit = Retrofit
            .Builder()
            .client(okHttpClient())
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
         retrofit.create(WeatherService::class.java)
        return retrofit.create(WeatherService::class.java)
    }

    private fun okHttpClient() : OkHttpClient {
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