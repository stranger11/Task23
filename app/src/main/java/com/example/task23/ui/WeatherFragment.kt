package com.example.task23.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task23.data.WeatherResponse
import com.example.task23.databinding.FragmentWeatherBinding
import com.example.task23.retrofit.WeatherService
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

class WeatherFragment : Fragment() {

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: Adapter
    private val okStatusCodes = 200..299

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        initRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        if (WeatherStore.weathers == null) {
            getCurrentData()
        } else {
            adapter.submitList(WeatherStore.weathers)
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = Adapter()
        binding.recyclerView.adapter = adapter
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
                        adapter.submitList(weatherResponse.list)
                        WeatherStore.weathers = weatherResponse.list
                    }
                }
                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_LONG).show()
                }
            })
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
}

object WeatherStore {
    var weathers : List<WeatherResponse.WeatherData>? = null
}