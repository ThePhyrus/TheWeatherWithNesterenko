package com.example.theweatherwithnesterenko.view

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.theweatherwithnesterenko.R
import com.example.theweatherwithnesterenko.databinding.FragmentMapsMainBinding
import com.example.theweatherwithnesterenko.repository.weather.City
import com.example.theweatherwithnesterenko.repository.weather.Weather
import com.example.theweatherwithnesterenko.utils.KEY_BUNDLE_WEATHER_FROM_LIST_TO_DETAILS
import com.example.theweatherwithnesterenko.utils.REQUEST_CODE_LOCATION
import com.example.theweatherwithnesterenko.view.details.DetailsFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import java.util.*

class MapsFragment : Fragment() {
    private var _binding: FragmentMapsMainBinding? = null
    private val binding: FragmentMapsMainBinding get() = _binding!!
    private lateinit var map: GoogleMap
    private val markers: ArrayList<Marker> = arrayListOf()

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Saint-Petersburg, Russia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        map = googleMap
        val saintPetersburg = LatLng(59.939099, 30.315877)
        map.addMarker(
            MarkerOptions().position(saintPetersburg).title("Marker in Saint-Petersburg")
        )
        map.moveCamera(CameraUpdateFactory.newLatLng(saintPetersburg))
        map.setOnMapLongClickListener {
            addMarkerToArray(it)
            drawLine()
        }
        map.setOnMapClickListener { //FIXME
            val weather = Weather(city = City(getAddressByLocation(it), it.latitude, it.longitude))
            requireActivity().supportFragmentManager.beginTransaction().add(
                R.id.container,
                DetailsFragment.newInstance(Bundle().apply {
                    putParcelable(KEY_BUNDLE_WEATHER_FROM_LIST_TO_DETAILS, weather)
                })
            ).addToBackStack("").commit()
        }
        map.uiSettings.isZoomControlsEnabled = true // появятся "+" и "-" для ZOOM
        map.uiSettings.isMyLocationButtonEnabled = true // а тут что?
        checkPermission()
//        map.isMyLocationEnabled = true // todo add permission check
    }

    private fun checkPermission() {
        context?.let {
            when {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED -> {
                    map.isMyLocationEnabled = true
                }
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                    explain()
                }
                else -> {
                    myRequestPermission()
                }
            }
        }
    }

    private fun myRequestPermission() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE_LOCATION)
    }

    private fun explain() {
        AlertDialog.Builder(requireContext())
            .setTitle("Нужен доступ к местополжению")
            .setMessage("Объяснение: вот зачем нам нужен доступ")
            .setPositiveButton("Предоставить доступ") { _, _ ->
                myRequestPermission()
            }
            .setNegativeButton("Не предоставлять") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()

    }

    private fun getAddressByLocation(location: LatLng): String {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        //todo add location.altitude?
        val addressText =
            geocoder.getFromLocation(
                location.latitude,
                location.longitude,
                1 //FIXME хватит 1?
            )[0].getAddressLine(0) //todo настроить отображения адреса
        return addressText
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapsMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        initView()
    }

    private fun initView() {
        binding.buttonSearch.setOnClickListener {
            val searchText = binding.searchAddress.text.toString()
            //todo HW провести проверку searchText на Null
            val geocoder = Geocoder(requireContext(), Locale.getDefault())
            //todo HW провести проверку results на Null & java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
            val results = geocoder.getFromLocationName(searchText, 1)
            val location = LatLng(
                results[0].latitude, results[0].longitude
            )
            map.addMarker(
                MarkerOptions().position(location)
                    .title(searchText)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_marker))
            )
            map.moveCamera(
                CameraUpdateFactory.newLatLngZoom(location, 15f)
            )
        }
    }

    private fun addMarkerToArray(location: LatLng) {
        val marker = setMarker(location, markers.size.toString(), R.drawable.ic_map_pin)
        markers.add(marker)
    }

    private fun drawLine() {
        var previousMarker: Marker? = null
        markers.forEach { currentPosition ->
            previousMarker?.let { previousPosition ->
                map.addPolyline(
                    PolylineOptions().add(previousPosition.position, currentPosition.position)
                        .color(Color.RED)
                        .width(5f)
                )
            }
            previousMarker = currentPosition
        }
    }

    private fun setMarker(
        location: LatLng,
        searchText: String,
        resourceId: Int
    ): Marker {
        return map.addMarker(
            MarkerOptions()
                .position(location)
                .title(searchText)
                .icon(BitmapDescriptorFactory.fromResource(resourceId))
        )!!
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}