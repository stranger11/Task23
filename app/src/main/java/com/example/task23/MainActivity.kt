package com.example.task23

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.task23.databinding.ActivityMainBinding
import com.example.task23.ui.WeatherFragment

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceWeatherFragment()
    }

    private fun replaceWeatherFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, WeatherFragment()).commit()
    }
}