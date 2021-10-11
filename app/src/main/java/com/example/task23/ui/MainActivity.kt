package com.example.task23.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task23.App.Companion.repositoryImpl
import com.example.task23.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: Adapter
    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        weatherViewModel = ViewModelProvider(this, WeatherViewModelFactory(repositoryImpl))
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

