package com.example.theweatherwithnesterenko.view.details

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.theweatherwithnesterenko.R
import com.example.theweatherwithnesterenko.databinding.FragmentDetailsBinding
import com.example.theweatherwithnesterenko.repository.*
import com.example.theweatherwithnesterenko.utils.*
import com.example.theweatherwithnesterenko.viewmodel.ResponseState
import com.google.android.material.snackbar.Snackbar

class DetailsFragment : Fragment(), OnServerResponse, OnServerResponseListener {

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val receiver = object : BroadcastReceiver() { // создаётся BR без создания класса (круто!)
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let { intent
                intent.getParcelableExtra<WeatherDTO>(KEY_BUNDLE_SERVICE_BROADCAST_WEATHER)?.let {
                    onResponse(it)
                }
            }
        }
    }

    lateinit var currentCityName: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(receiver,
            IntentFilter(KEY_WAVE_SERVICE_BROADCAST)
        )
        arguments?.getParcelable<Weather>(KEY_BUNDLE_WEATHER_FROM_LIST_TO_DETAILS)?.let {
            currentCityName = it.city.name
                /*WeatherLoader(
                this@DetailsFragment,
                this@DetailsFragment
            ).loadWeather(
                it.city.lat,
                it.city.lon
            )*/ // на 6-ом занятии этот подход удалили. Чем он плох, я понять не успел, но выглядит он странновато.
            //Теперь понял. Запрос на сервер будет выполнять DetailsService.

            requireActivity().startService(Intent(requireContext(),DetailsService::class.java).apply {
                putExtra(KEY_BUNDLE_LAT, it.city.lat)
                putExtra(KEY_BUNDLE_LON, it.city.lon)
            })
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
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(receiver)
    }

    override fun onError(error: ResponseState) {
        //FIXME не разобрался с этим способом вывода ошибки
    }
}
