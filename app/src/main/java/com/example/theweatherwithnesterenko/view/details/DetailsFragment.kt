package com.example.theweatherwithnesterenko.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.theweatherwithnesterenko.R
import com.example.theweatherwithnesterenko.databinding.FragmentDetailsBinding
import com.example.theweatherwithnesterenko.repository.TheWeather
import com.example.theweatherwithnesterenko.utils.KEY_BUNDLE_WEATHER
import com.google.android.material.snackbar.Snackbar


class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() {
            return _binding!!
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val weather: TheWeather =
            requireArguments().getParcelable<TheWeather>(KEY_BUNDLE_WEATHER)!!
        renderData(weather)
    }

    private fun renderData(weatherDataForRendering: TheWeather) = with(binding) {
        layoutZagruzki.visibility = View.GONE
        cityName.text = weatherDataForRendering.city.name.toString()
        temperatureValue.text = weatherDataForRendering.temperature.toString()
        feelsLikeValue.text = weatherDataForRendering.feelsLike.toString()
        cityCoordinates.text = "${weatherDataForRendering.city.lat} ${weatherDataForRendering.city.lon}"
        Snackbar.make(mainView, R.string.data_rendering_success, Snackbar.LENGTH_LONG).show()
    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle): DetailsFragment { //FIXME Bundle?
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}