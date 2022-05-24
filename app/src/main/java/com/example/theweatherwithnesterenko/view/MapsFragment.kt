package com.example.theweatherwithnesterenko.view

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.theweatherwithnesterenko.R
import com.example.theweatherwithnesterenko.databinding.FragmentMapsMainBinding
import com.example.theweatherwithnesterenko.repository.weather.City
import com.example.theweatherwithnesterenko.repository.weather.Weather
import com.example.theweatherwithnesterenko.utils.KEY_BUNDLE_WEATHER_FROM_LIST_TO_DETAILS
import com.example.theweatherwithnesterenko.utils.REQUEST_CODE_LOCATION
import com.example.theweatherwithnesterenko.utils.TAG
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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

    private val callback = OnMapReadyCallback { googleMap ->
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
        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.isMyLocationButtonEnabled = true
        checkPermission()
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


    private fun getAddressByLocation(location: LatLng): String {//FIXME
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        return geocoder.getFromLocation(
            location.latitude,
            location.longitude,
            1
        )[0].getAddressLine(0)
    }


    private fun initView() {//FIXME
        binding.buttonSearch.setOnClickListener {
            val searchText = binding.searchAddress.text.toString()
            if (searchText.isNotEmpty()){
                val geocoder = Geocoder(requireContext(), Locale.getDefault())
                val results = geocoder.getFromLocationName(searchText, 1)
                if (results.size > 0) {
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
                }else {
                    Log.d(TAG, "initView() called")
                }
            }
            Log.d(TAG, "initView() called")

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


}