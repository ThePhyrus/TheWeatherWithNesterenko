package com.example.theweatherwithnesterenko.view.weatherlist


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.theweatherwithnesterenko.R
import com.example.theweatherwithnesterenko.databinding.FragmentWeatherListBinding
import com.example.theweatherwithnesterenko.repository.weather.City
import com.example.theweatherwithnesterenko.repository.weather.Weather
import com.example.theweatherwithnesterenko.utils.KEY_BUNDLE_WEATHER_FROM_LIST_TO_DETAILS
import com.example.theweatherwithnesterenko.utils.REQUEST_CODE_LOCATION

import com.example.theweatherwithnesterenko.utils.TAG
import com.example.theweatherwithnesterenko.view.details.DetailsFragment
import com.example.theweatherwithnesterenko.viewmodel.states.AppState
import com.example.theweatherwithnesterenko.viewmodel.MainViewModel
import java.util.*


class WeatherListFragment : Fragment(),
    OnItemListClickListener {

    private var _binding: FragmentWeatherListBinding? = null
    private val binding: FragmentWeatherListBinding get() = _binding!!
    private val adapter = WeatherListAdapter(this)

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherListBinding.inflate(inflater, container, false)
        return binding.root
    }

    var isRussian = true
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        val observer = { data: AppState -> renderData(data) }
        viewModel.getData().observe(viewLifecycleOwner, observer)
        doSetupFABCities()
        setupFABGetCurrentLocationInfo()
        viewModel.getWeatherRussia()
    }

    private fun initRecyclerView() {
        binding.recyclerView.also {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupFABGetCurrentLocationInfo() {
        binding.mainFragmentFABLocation.setOnClickListener {
            checkPermissionToGetGeolocation()
        }
    }

    private fun checkPermissionToGetGeolocation() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getLocation()
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            explain()
        } else {
            mRequestPermission()
        }
    }

    private fun explain() {
        AlertDialog.Builder(requireContext())
            .setTitle("Нужен доступ к геолокации!")
            .setMessage("Нам нам нужен доступ к геолокации, чтобы Вы могли узнать адрес Вашего местонахождения и сомнительную информацию о погоде.")
            .setPositiveButton("Предоставить доступ") { _, _ ->
                mRequestPermission()
            }
            .setNegativeButton("Не предоставлять") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    private fun mRequestPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_CODE_LOCATION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_LOCATION) {
            for (i in permissions.indices) {
                if (permissions[i] == Manifest.permission.ACCESS_FINE_LOCATION
                    && grantResults[i] == PackageManager.PERMISSION_GRANTED
                ) {
                    getLocation()
                } else {
                    explain()
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }


    private fun getAddressByLocation(location: Location) { //todo настроить отображения адреса getAddressLine
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        //todo add location.altitude?
        val timeStump = System.currentTimeMillis()
        Thread {
            val addressText =
                geocoder.getFromLocation(
                    location.latitude,
                    location.longitude,
                    1 //FIXME хватит 1?
                )[0].getAddressLine(0)
            requireActivity().runOnUiThread {
                showAddressDialog(addressText, location)
            }
        }.start()
        Log.d(TAG, "getAddressByLocation: ${System.currentTimeMillis() - timeStump}")
    }

    private val locationListenerTime = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            Log.d(TAG, "onLocationChangedByTime: $location")
            getAddressByLocation(location)
        }

        override fun onProviderEnabled(provider: String) {
            super.onProviderEnabled(provider)
        }

        override fun onProviderDisabled(provider: String) {
            super.onProviderDisabled(provider)
        }
    }

    private val locationListenerDistance = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            Log.d(TAG, "onLocationChangedByDistance: $location")
            getAddressByLocation(location)
        }

        override fun onProviderEnabled(provider: String) {
            super.onProviderEnabled(provider)
        }

        override fun onProviderDisabled(provider: String) {
            super.onProviderDisabled(provider)
        }
    }

    @SuppressLint("MissingPermission") // разрешения проверенны в  fun checkPermission()
    private fun getLocation() {
        context?.let {
            val locationManager = it.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                val providerGPS =
                    locationManager.getProvider(LocationManager.GPS_PROVIDER) // Why getBestProvider() does not work with LocationManager.GPS_PROVIDER
                providerGPS?.let {
                    locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        1000000L, //time between location request
                        10f, //distance between location request
                        locationListenerTime
                    )
                }
                /*providerGPS?.let {
                    locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        0L, //time between location request
                        10f, //distance between location request
                        locationListenerDistance
                    )
                }*/
            }
        }
        Log.d(TAG, "getLocation: did something")
    }

    private fun doSetupFABCities() {//FIXME shared pref
        binding.floatingActionButton.setOnClickListener {
            isRussian = !isRussian

       /*     isRussian = requireActivity().getSharedPreferences(Settings.SHARED_PREF, Context.MODE_PRIVATE)
                .getBoolean(Settings.SETTING_RUS, Settings.settingRus)*/

            if (isRussian) {
                viewModel.getWeatherRussia()
                binding.floatingActionButton.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_russia
                    )
                )
            } else {
                binding.floatingActionButton.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_earth
                    )
                )
                viewModel.getWeatherWorld()
            }
        }
    }

    private fun renderData(data: AppState) {
        when (data) {
            is AppState.Success -> {
                with(binding) {
                    loadingLayout.visibility = View.GONE
                }
                adapter.setData(data.weatherList)
            }
            is AppState.Error -> {
                with(binding) {
                    loadingLayout.visibility = View.GONE

                }
            }
            is AppState.Loading -> {
                with(binding) {
                    loadingLayout.visibility = View.VISIBLE
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = WeatherListFragment()
    }

    override fun onItemClick(weather: Weather) {
        requireActivity().supportFragmentManager.beginTransaction().add(
            R.id.container,
            DetailsFragment.newInstance(Bundle().apply {
                putParcelable(KEY_BUNDLE_WEATHER_FROM_LIST_TO_DETAILS, weather)
            })
        ).addToBackStack("").commit()
    }

    private fun showAddressDialog(address: String, location: Location) {
        activity?.let {
            AlertDialog.Builder(it)
                .setTitle(getString(R.string.dialog_address_title))
                .setMessage(address)
                .setPositiveButton(getString(R.string.dialog_address_get_weather)) { _, _ ->
                    onItemClick(
                        Weather(
                            City(
                                address,
                                location.latitude,
                                location.longitude
                            )
                        )
                    )
                }
                .setNegativeButton(getString(R.string.dialog_button_close)) { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        }
    }
}
