package com.example.task23.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task23.App.Companion.repositoryImpl
import com.example.task23.R
import com.example.task23.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

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

        weatherViewModel.eventErrorNetwork.observe(this, {
            it.getContentIfNotHandled()?.let {
                showSnackbar()
            }
        })
    }

    private fun observeWeather() {
        weatherViewModel.weathers.observe(this, { weatherList ->
            adapter.submitList(weatherList)
        })
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = Adapter{ city, date, temp ->
            sendData(city, date, temp)
        }
        binding.recyclerView.adapter = adapter
    }

    private fun sendData(
        city : String,
        date: String,
        temp: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            val dataArray = arrayListOf(city, date, temp)
            putExtra(Intent.EXTRA_TEXT, dataArray.toString())
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun showSnackbar() {
        val snackbar = Snackbar.make(
            binding.root,
            R.string.from_database,
            Snackbar.LENGTH_LONG)
        snackbar.show()
    }
}



