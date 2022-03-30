package com.example.theweatherwithnesterenko.view.weatherlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.theweatherwithnesterenko.R
import com.example.theweatherwithnesterenko.databinding.FragmentWeatherListBinding
import com.example.theweatherwithnesterenko.repository.TheWeather
import com.example.theweatherwithnesterenko.utils.KEY_BUNDLE_WEATHER
import com.example.theweatherwithnesterenko.view.details.DetailsFragment
import com.example.theweatherwithnesterenko.viewmodel.AppState
import com.example.theweatherwithnesterenko.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class WeatherListFragment : Fragment(), OnItemListClickListener {


    private var _binding: FragmentWeatherListBinding? = null
    private val binding: FragmentWeatherListBinding
        get() {
            return _binding!!
        }

    private val theAdapter = WeatherListAdapter(this)

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycleView() // TODO HW вынесты в initRecycler()
        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val observer = object : Observer<AppState> {
            override fun onChanged(data: AppState) {
                renderData(data)
            }
        }
        viewModel.getData().observe(viewLifecycleOwner, observer)

        binding.floatingActionButton.setOnClickListener {
            isRussian = !isRussian
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
        viewModel.getWeatherRussia()
    }

    private fun initRecycleView() {
        binding.krutilcaView.adapter = theAdapter
    }

    private fun renderData(someData: AppState) = with(binding) {
        when (someData) {
            is AppState.FatalError -> {
                layoutZagruzki.visibility = View.GONE
                Snackbar.make(
                    binding.root,
                    "${R.string.matrix_has_you} ${someData.error}",
                    Snackbar.LENGTH_LONG
                )
                    .show()
            }
            is AppState.LoadingProcess -> {
                layoutZagruzki.visibility = View.VISIBLE
            }
            is AppState.Success -> {
                layoutZagruzki.visibility = View.GONE
                theAdapter.setData(someData.weatherList)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = WeatherListFragment()
    }

    override fun onItemClick(weather: TheWeather) { //FIXME что такое Bundle?
        val bundle = Bundle()
        bundle.putParcelable(KEY_BUNDLE_WEATHER, weather)
        requireActivity().supportFragmentManager.beginTransaction().replace(
            R.id.box_for_fragment,
            DetailsFragment.newInstance(bundle)
        ).addToBackStack("").commit()
    }
}