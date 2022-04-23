package com.example.theweatherwithnesterenko.view.details

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.theweatherwithnesterenko.BuildConfig
import com.example.theweatherwithnesterenko.R
import com.example.theweatherwithnesterenko.databinding.FragmentDetailsBinding
import com.example.theweatherwithnesterenko.repository.*
import com.example.theweatherwithnesterenko.repository.dto.WeatherDTO
import com.example.theweatherwithnesterenko.utils.*
import com.example.theweatherwithnesterenko.viewmodel.ResponseState
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException


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

                // запрос на сервер прямо из фрагмента! УЖАС ПРОСТО!!!
                /*WeatherLoader(
                this@DetailsFragment,
                this@DetailsFragment
            ).loadWeather(
                it.city.lat,
                it.city.lon
            )*/ // на 6-ом занятии этот подход удалили. Чем он плох, я понять не успел, но выглядит он странновато.
            //Теперь понял. Запрос на сервер будет выполнять DetailsService.


            // закомментирован запрос погоды с сервера при помощи сервиса
            /*requireActivity().startService(Intent(requireContext(),DetailsService::class.java).apply {
                putExtra(KEY_BUNDLE_LAT, it.city.lat)
                putExtra(KEY_BUNDLE_LON, it.city.lon)
            })*/ // закомментирован запрос погоды с сервера при помощи сервиса

            getWeather(it.city.lat,it.city.lon)
        }
    }

    private fun getWeather(lat:Double, lon:Double){
        binding.loadingLayout.visibility = View.VISIBLE //renderData(LoadingState)

        val client = OkHttpClient() // создал клиент
        val requestBuilder = Request.Builder() // создал "строителя" запросов на сервер

        requestBuilder.addHeader(X_YANDEX_API_KEY, BuildConfig.WEATHER_API_KEY) // заголовок
        requestBuilder.url("$MASTER_DOMAIN${YANDEX_ENDPOINT}lat=$lat&lon=$lon") // адрес

        val request = requestBuilder.build() // создали запрос

        val callback:Callback = object : Callback{ // сюда вернётся ответ
            override fun onFailure(call: Call, e: IOException) {
                //todo HW
                //renderData() // сюда передавать какой-то ещё не созданный state
                binding.loadingLayout.visibility = View.GONE // поток не тот
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val weatherDTO: WeatherDTO = Gson().fromJson(response.body()?.string(), WeatherDTO::class.java)
                    requireActivity().runOnUiThread {
                        renderData(weatherDTO)
                    }
                }else {
                    //todo HW
                }
            }
        }
        val call = client.newCall(request) // создали звоночек

        /*Thread{
            // work 1
            val response = call.execute() // синхронный запрос
            // work 2
        }.start()*/

        call.enqueue(callback) // вызвать звоночек, поместить его в очередь, ответ вернуть в callback
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
