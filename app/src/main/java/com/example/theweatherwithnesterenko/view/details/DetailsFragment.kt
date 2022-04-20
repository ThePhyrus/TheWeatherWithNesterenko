package com.example.theweatherwithnesterenko.view.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.theweatherwithnesterenko.R
import com.example.theweatherwithnesterenko.databinding.FragmentDetailsBinding
import com.example.theweatherwithnesterenko.repository.OnServerResponse
import com.example.theweatherwithnesterenko.repository.Weather
import com.example.theweatherwithnesterenko.repository.WeatherDTO
import com.example.theweatherwithnesterenko.repository.WeatherLoader
import com.example.theweatherwithnesterenko.utils.KEY_BUNDLE_WEATHER
import com.google.android.material.snackbar.Snackbar

class DetailsFragment : Fragment(), OnServerResponse {

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    lateinit var currentCityName: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Weather>(KEY_BUNDLE_WEATHER)?.let {
            currentCityName = it.city.name
            WeatherLoader(this@DetailsFragment).loadWeather(it.city.lat, it.city.lon)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun renderData(weather: WeatherDTO) {
        with(binding) {
            loadingLayout.visibility = View.GONE
            cityName.text = currentCityName
            temperatureValue.text = weather.factDTO.temperature.toString()
            feelsLikeValue.text = weather.factDTO.feelsLike.toString()
            cityCoordinates.text = "${weather.infoDTO.lat} ${weather.infoDTO.lon}"
            Snackbar.make(mainView, R.string.data_rendering_success, Snackbar.LENGTH_LONG)
                .show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onResponse(weatherDTO: WeatherDTO) {
        renderData(weatherDTO)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
