package com.example.theweatherwithnesterenko.view.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.theweatherwithnesterenko.R
import com.example.theweatherwithnesterenko.databinding.FragmentDetailsBinding
import com.example.theweatherwithnesterenko.repository.Weather
import com.example.theweatherwithnesterenko.utils.KEY_BUNDLE_WEATHER
import com.google.android.material.snackbar.Snackbar


class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding get() = _binding!!

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
        arguments?.getParcelable<Weather>(KEY_BUNDLE_WEATHER)?.let {
            renderData(it)
        }

    }

    @SuppressLint("SetTextI18n") //FIXME это не я, это студия почти сама. Так можно вообще?
    private fun renderData(weather: Weather) = with(binding) {
        loadingLayout.visibility = View.GONE
        cityName.text = weather.city.name.toString()
        temperatureValue.text = weather.temperature.toString()
        feelsLikeValue.text = weather.feelsLike.toString()
        cityCoordinates.text =
            "${weather.city.lat} " +
                    "${weather.city.lon}"
        Snackbar.make(mainView, R.string.data_rendering_success, Snackbar.LENGTH_SHORT)
            .show()
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