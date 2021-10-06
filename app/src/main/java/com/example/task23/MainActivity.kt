package com.example.task23

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task23.App.Companion.repository
import com.example.task23.data.WeatherRepository
import com.example.task23.databinding.ActivityMainBinding
import com.example.task23.ui.Adapter
import com.example.task23.ui.Weather
import com.example.task23.ui.WeatherViewModel
import com.example.task23.ui.WeatherViewModelFactory

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: Adapter
    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        weatherViewModel = ViewModelProvider(this, WeatherViewModelFactory(repository))
            .get(WeatherViewModel::class.java)
        initRecyclerView()
        observeWeather()
    }

    private fun observeWeather() {
        weatherViewModel.weathers.observe(this, { weatherList ->
            adapter.submitList(weatherList)
        })
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = Adapter()
        binding.recyclerView.adapter = adapter
    }
}

