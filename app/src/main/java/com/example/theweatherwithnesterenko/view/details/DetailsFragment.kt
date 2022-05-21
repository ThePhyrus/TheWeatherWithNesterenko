package com.example.theweatherwithnesterenko.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import coil.request.ImageRequest
import com.example.theweatherwithnesterenko.R
import com.example.theweatherwithnesterenko.databinding.FragmentDetailsBinding
import com.example.theweatherwithnesterenko.repository.weather.Weather
import com.example.theweatherwithnesterenko.utils.*
import com.example.theweatherwithnesterenko.viewmodel.DetailsViewModel
import com.example.theweatherwithnesterenko.viewmodel.states.DetailsState
import com.google.android.material.snackbar.Snackbar


class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, object : Observer<DetailsState> {
            override fun onChanged(t: DetailsState) {
                renderData(t)
            }
        })
        arguments?.getParcelable<Weather>(KEY_BUNDLE_WEATHER_FROM_LIST_TO_DETAILS)?.let {
            Thread {
                viewModel.getWeather(it.city)
            }.start()
        }
    }


    private fun renderData(detailsState: DetailsState) {
        when (detailsState) {
            is DetailsState.Error -> {
                binding.loadingLayout.visibility = View.VISIBLE
                binding.root.showSnackBarWithAction(
                    getString(R.string.data_rendering_error),
                    getString(R.string.try_again), {
                    }, Snackbar.LENGTH_LONG
                )
            }
            DetailsState.Loading -> {
                with(binding) {
                    loadingLayout.visibility = View.VISIBLE
                }
            }
            is DetailsState.Success -> {
                val weather = detailsState.weather
                with(binding) {
                    loadingLayout.visibility = View.GONE
                    cityName.text = weather.city.name
                    temperatureValue.text = weather.temperature.toString()
                    feelsLikeValue.text = weather.feelsLike.toString()
                    cityCoordinates.text = "${weather.city.lat} ${weather.city.lon}"
                    headerCityIcon.load("$FREEPNGIMG_DOMAIN$FREEPNGIMG_ENDPOINT")
                    icon.loadSvg(
                        "${YASTATIC_DOMAIN}${YANDEX_WEATHER_ICON_ENDPOINT}${weather.icon}${DOT_SVG}"
                    )
                }
            }
        }
    }


    private fun View.showSnackBarWithoutAction( // работает))
        text: String,
        length: Int = Snackbar.LENGTH_INDEFINITE
    ) {
        Snackbar.make(binding.root, text, length).show()
    }


    private fun View.showSnackBarWithAction( //FIXME вроде бы правильно, но с вызовом трудности.
        text: String,
        actionText: String,
        action: (View) -> Unit,
        length: Int = Snackbar.LENGTH_INDEFINITE
    ) {
        Snackbar.make(this, text, length).setAction(actionText, action).show()
    }


    private fun ImageView.loadSvg(url: String) {
        val imageLoader = ImageLoader.Builder(this.context)
            .componentRegistry { add(SvgDecoder(this@loadSvg.context)) }
            .build()
        val request = ImageRequest.Builder(this.context)
            .crossfade(true)
            .crossfade(500)
            .data(url)
            .target(this)
            .build()
        imageLoader.enqueue(request)
    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
