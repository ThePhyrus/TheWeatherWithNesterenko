package com.example.theweatherwithnesterenko.view.weatherlist


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.theweatherwithnesterenko.R
import com.example.theweatherwithnesterenko.databinding.FragmentWeatherListBinding
import com.example.theweatherwithnesterenko.repository.Weather
import com.example.theweatherwithnesterenko.utils.KEY_BUNDLE_WEATHER
import com.example.theweatherwithnesterenko.view.details.DetailsFragment
import com.example.theweatherwithnesterenko.viewmodel.AppState
import com.example.theweatherwithnesterenko.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class WeatherListFragment : Fragment(), OnItemListClickListener {

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
        doSetupFAB()
        viewModel.getWeatherRussia()
    }

    private fun initRecyclerView() {
        binding.recyclerView.also {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun doSetupFAB() =
        with(binding) { //FIXME применение with тут не лишнее? Чую, что лишнее, а объяснить не могу толком.
            floatingActionButton.setOnClickListener {
                isRussian = !isRussian
                if (isRussian) {
                    viewModel.getWeatherRussia()
                    floatingActionButton.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_russia
                        )
                    )
                } else {
                    floatingActionButton.setImageDrawable(
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
            is AppState.Error -> {
                with(binding) {
                    loadingLayout.visibility = View.GONE
                }
                Snackbar.make(
                    binding.root,
                    "${R.string.matrix_has_you}" + "${data.error}",
                    Snackbar.LENGTH_LONG
                )
                    .show()
            }
            is AppState.Loading -> {
                with(binding) {
                    loadingLayout.visibility = View.VISIBLE
                }
            }
            is AppState.Success -> {
                with(binding) {
                    loadingLayout.visibility = View.GONE
                }
                adapter.setData(data.weatherList)
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
                putParcelable(KEY_BUNDLE_WEATHER, weather)
            })
        ).addToBackStack("").commit()
    }
}
