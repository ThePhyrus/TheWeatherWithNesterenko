package com.example.theweatherwithnesterenko.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.theweatherwithnesterenko.databinding.FragmentDetailsBinding
import com.example.theweatherwithnesterenko.repository.TheWeather
import com.example.theweatherwithnesterenko.utils.KEY_BUNDLE_WEATHER
import com.google.android.material.snackbar.Snackbar

private const val SNACKBAR_SUCCESS_RENDERING: String = "Data rendering is complete"

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
        val theWeather: TheWeather = requireArguments().getParcelable<TheWeather>(KEY_BUNDLE_WEATHER)!!
        renderData(theWeather)
    }

    private fun renderData(theWeather: TheWeather) = with(binding) {
        layoutZagruzki.visibility = View.GONE
        cityName.text = theWeather.city.name.toString()
        temperatureValue.text = theWeather.temperature.toString()
        feelsLikeValue.text = theWeather.feelsLike.toString()
        cityCoordinates.text = "${theWeather.city.lat} ${theWeather.city.lon}"
        //FIXME текст для снекбара в ресурсы лучше выносить или в константы?
        Snackbar.make(mainView, SNACKBAR_SUCCESS_RENDERING, Snackbar.LENGTH_LONG).show()
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