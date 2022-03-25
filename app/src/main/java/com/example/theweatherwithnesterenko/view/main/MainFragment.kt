package com.example.theweatherwithnesterenko.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.theweatherwithnesterenko.databinding.FragmentMainBinding
import com.example.theweatherwithnesterenko.viewmodel.AppState
import com.example.theweatherwithnesterenko.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {


    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val observer = object : Observer<AppState> {
            override fun onChanged(data: AppState) {
                renderData(data)
            }
        }
        viewModel.getData().observe(viewLifecycleOwner, observer)
        viewModel.getWeather()
    }

    private fun renderData(data: AppState) {
        when (data) {
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar.make(binding.mainView, "Не получилось ${data.error}", Snackbar.LENGTH_LONG)
                    .show()
            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Success -> {
                binding.loadingLayout.visibility = View.GONE
                binding.cityName.text = data.weatherData.city.name.toString()
                binding.temperatureValue.text = data.weatherData.temperature.toString()
                binding.feelsLikeValue.text = data.weatherData.feelsLike.toString()
                binding.cityCoordinates.text =
                    "${data.weatherData.city.lat} ${data.weatherData.city.lon}"
                Snackbar.make(binding.mainView, "Получилось", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}