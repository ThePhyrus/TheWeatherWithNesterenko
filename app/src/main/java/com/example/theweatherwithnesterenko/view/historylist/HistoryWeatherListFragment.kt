package com.example.theweatherwithnesterenko.view.historylist


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.theweatherwithnesterenko.databinding.FragmentHistoryWeatherListBinding
import com.example.theweatherwithnesterenko.view.weatherlist.HistoryWeatherListAdapter
import com.example.theweatherwithnesterenko.viewmodel.AppState
import com.example.theweatherwithnesterenko.viewmodel.HistoryViewModel
import com.google.android.material.snackbar.Snackbar


class HistoryWeatherListFragment : Fragment(){

    private var _binding: FragmentHistoryWeatherListBinding? = null
    private val binding: FragmentHistoryWeatherListBinding get() = _binding!!
    private val adapter = HistoryWeatherListAdapter()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryWeatherListBinding.inflate(inflater, container, false)
        return binding.root
    }

    var isRussian = true
    private val viewModel: HistoryViewModel by lazy {
        ViewModelProvider(this).get(HistoryViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        val observer = { data: AppState -> renderData(data) }
        viewModel.getData().observe(viewLifecycleOwner, observer)
        viewModel.getAll()
    }

    private fun initRecyclerView() {
        binding.recyclerView.also {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun renderData(data: AppState) {
        when (data) {
            is AppState.Error -> {
            //  binding.loadingLayout.visibility = View.GONE
                Snackbar.make(binding.root, "@@@@@@@ ${data.error}", Snackbar.LENGTH_SHORT).show()
            }
            is AppState.Loading -> {
//                with(binding) {
//                    loadingLayout.visibility = View.VISIBLE
//                }
            }
            is AppState.Success -> {
//                with(binding) {
//                    loadingLayout.visibility = View.GONE
//                }
                adapter.setData(data.weatherList)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HistoryWeatherListFragment()
    }

}
