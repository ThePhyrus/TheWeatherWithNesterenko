package com.example.theweatherwithnesterenko.view.adapters

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
import com.example.theweatherwithnesterenko.repository.Weather
import com.example.theweatherwithnesterenko.utils.KEY_BUNDLE_WEATHER
import com.example.theweatherwithnesterenko.view.fragments.DetailsFragment
import com.example.theweatherwithnesterenko.view.listeners.OnItemListClickListener
import com.example.theweatherwithnesterenko.viewmodel.states.AppState
import com.example.theweatherwithnesterenko.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class WeatherListFragment : Fragment(), OnItemListClickListener {


    private var _binding: FragmentWeatherListBinding? = null
    private val binding: FragmentWeatherListBinding
        get() {
            return _binding!!
        }

    private val myAdapter = WeatherListAdapter(this)
    private var isRussian: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycleView() //FIXME правильно сделал?
        val myViewModelInWeatherListFragment =
            ViewModelProvider(this).get(MainViewModel::class.java)

        val myObserver = object : Observer<AppState> {
            override fun onChanged(data: AppState) {
                doRenderDataAtWeatherListFragment(data)
            }
        }
        myViewModelInWeatherListFragment.getData().observe(viewLifecycleOwner, myObserver)
        onClickFloatingButton(myViewModelInWeatherListFragment)
        myViewModelInWeatherListFragment.getWeatherRussia()
    }

    private fun onClickFloatingButton(viewModel: MainViewModel) {
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
    }

    private fun initRecycleView() {
        binding.rcView.adapter = myAdapter
    }

    private fun doRenderDataAtWeatherListFragment(dataForRenderingAtWeatherListFragment: AppState) =
        with(binding) {
            when (dataForRenderingAtWeatherListFragment) {
                is AppState.FatalError -> {
                    loadingLayout.visibility = View.GONE
                    Snackbar.make(
                        binding.root,
                        "${R.string.matrix_has_you} " +
                                "${dataForRenderingAtWeatherListFragment.fatalError}",
                        Snackbar.LENGTH_LONG
                    )
                        .show()
                }
                is AppState.LoadingProcess -> {
                    loadingLayout.visibility = View.VISIBLE
                }
                is AppState.Success -> {
                    loadingLayout.visibility = View.GONE
                    myAdapter.doSetData(dataForRenderingAtWeatherListFragment.myListWeather)
                }
            }
        }

    companion object {
        @JvmStatic
        fun newInstance() = WeatherListFragment()
    }

    override fun onItemClick(bundledWeather: Weather) { //FIXME что такое Bundle?
        val bundle = Bundle()
        bundle.putParcelable(KEY_BUNDLE_WEATHER, bundledWeather)
        requireActivity().supportFragmentManager.beginTransaction().replace(
            R.id.mainContainer,
            DetailsFragment.newInstance(bundle)
        ).addToBackStack("${R.string.empty_stroke}").commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}